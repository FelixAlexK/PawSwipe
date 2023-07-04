package de.hhn.softwarelabor.pawswipeapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import de.hhn.softwarelabor.pawswipeapp.api.animal.AnimalProfileApi
import de.hhn.softwarelabor.pawswipeapp.api.data.AnimalProfileData
import de.hhn.softwarelabor.pawswipeapp.api.filter.FilterApi
import de.hhn.softwarelabor.pawswipeapp.api.filter.FilterEnum
import de.hhn.softwarelabor.pawswipeapp.api.like.LikeApi
import de.hhn.softwarelabor.pawswipeapp.utils.AppData
import de.hhn.softwarelabor.pawswipeapp.utils.Base64Utils
import kotlin.math.abs


/**
 * Match activity is an activity for displaying and interacting with animal profiles.
 * @author Felix Kuhbier & Leo Kalmbach
 * @since 2023.06.12
 */
class MatchActivity : AppCompatActivity(), CardStackListener {

    private lateinit var filterBtn: Button
    private lateinit var animalListBtn: Button
    private lateinit var likeBtn: ImageButton
    private lateinit var dislikeBtn: ImageButton
    private lateinit var matchBtn: Button
    private lateinit var imageList: List<Int>

    private var profileId: Int = 0
    private var likeApi: LikeApi = LikeApi()
    private var animalId: Int = 0

    private var backPressedOnce = false
    private val timerDuration = 3000 // 3 Sekunden

    private val latLongUtil = LatLongUtil(this)

    private val animalProfileApi = AnimalProfileApi()
    private val filterAPI = FilterApi()

    private lateinit var adapter: CardAdapter
    private lateinit var cardStackView: CardStackView
    private lateinit var layoutManager: CardStackLayoutManager
    private var currentPosition = 0
    var isDragging = false
    var startX = 0f
    var startY = 0f
    
    
    private lateinit var detailedAnimalName: TextView
    private lateinit var detailedAnimalPicture: ImageView
    private lateinit var detailedAnimalBreed: TextView
    private lateinit var detailedAnimalSpecies: TextView
    private lateinit var detailedAnimalBirthday: TextView
    private lateinit var detailedAnimalPreExistingIllness: TextView
    private lateinit var detailedAnimalColor: TextView
    private lateinit var detailedAnimalGender: TextView
    private lateinit var detailedShelterPhone: TextView
    private lateinit var detailedShelterEmail: TextView
    private lateinit var detailedShelterPhoneHint: TextView
    private lateinit var detailedShelterEmailHint: TextView
    private lateinit var detailedAnimalDislikeButton: ImageButton
    
    
    private lateinit var currentAnimal: AnimalProfileData
    
    


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        if (backPressedOnce) {
            finishAffinity()    // Beendet alle Activities und die App
            return
        }

        backPressedOnce = true
        Toast.makeText(
            this, getString(R.string.zum_beenden_der_app), Toast.LENGTH_SHORT
        ).show()

        Handler(Looper.getMainLooper()).postDelayed({
            backPressedOnce = false
        }, timerDuration.toLong())

    }

    override fun onResume() {
        super.onResume()
        profileId = AppData.getID(this@MatchActivity)
    }

    
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        cardStackView = findViewById(R.id.matchCardStackView)


        layoutManager = CardStackLayoutManager(this@MatchActivity, this@MatchActivity)
        layoutManager.setStackFrom(StackFrom.None)
        layoutManager.setVisibleCount(1)
        cardStackView.layoutManager = layoutManager
        
        cardStackView.layoutParams.width = resources.displayMetrics.widthPixels
        cardStackView.layoutParams.height = (resources.displayMetrics.widthPixels*1.2f).toInt()
        
        

        getAllAnimals()

        // If a bug brings a Shelter to the Match Activity, it closes the Activity
        if (AppData.getDiscriminator(this@MatchActivity) == "shelter") {
            finish()
        }


        profileId = AppData.getID(this)
        animalId = intent.getIntExtra("animal_id", 0)

        filterBtn = findViewById(R.id.filter_btn)
        animalListBtn = findViewById(R.id.animalList_btn2)
        likeBtn = findViewById(R.id.like_button)
        dislikeBtn = findViewById(R.id.dislike_button)
        matchBtn = findViewById(R.id.matching_btn2)
        imageList = ArrayList()

        if (AppData.getDiscriminator(this@MatchActivity) == "shelter") {
            matchBtn.isClickable = false
            matchBtn.setBackgroundColor(Color.TRANSPARENT)
            matchBtn.background = null
        }

        filterBtn.setOnClickListener {
            val intent = Intent(this@MatchActivity, FilterActivity::class.java)
            startActivity(intent)
        }

        animalListBtn.setOnClickListener {
            val intent = Intent(this@MatchActivity, AnimalListActivity::class.java)
            intent.putExtra("id", profileId)
            startActivity(intent)
        }

        likeBtn.setOnClickListener {
            val swipeAnimationSetting =
                SwipeAnimationSetting.Builder().setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration).setInterpolator(AccelerateInterpolator())
                    .build()
            layoutManager.setSwipeAnimationSetting(swipeAnimationSetting)
            cardStackView.swipe()
        }

        dislikeBtn.setOnClickListener {
            val swipeAnimationSetting = SwipeAnimationSetting.Builder().setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration).setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(swipeAnimationSetting)
            cardStackView.swipe()
        }

        cardStackView.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (isCardStackEmpty()) {
                        return@setOnTouchListener true
                    }
                    isDragging = false
                    startX = motionEvent.x
                    startY = motionEvent.y
                    true
                }
                
                MotionEvent.ACTION_MOVE -> {
                    if (isCardStackEmpty()) {
                        return@setOnTouchListener true
                    }
                    if (abs(motionEvent.x - startX) > 10 || abs(motionEvent.y) > 10) {
                        isDragging = true
                    }
                    false
                }
                
                MotionEvent.ACTION_UP -> {
                    if (isCardStackEmpty()) {
                        return@setOnTouchListener true
                    }
                    if (!isDragging && currentPosition < adapter.itemCount) {
                        currentAnimal = adapter.getAnimal(currentPosition)
    
                        val customLayout =
                            layoutInflater.inflate(R.layout.activity_detailed_animal_profile, null)
    
    
                        detailedAnimalName = customLayout.findViewById(R.id.detailedAnimalName_textView)
                        detailedAnimalPicture =
                            customLayout.findViewById(R.id.detailedAnimalPicture_imageView)
                        detailedAnimalBreed =
                            customLayout.findViewById(R.id.detailedAnimalBreed_textView)
                        detailedAnimalBirthday =
                            customLayout.findViewById(R.id.detailedAnimalBirthday_textView)
                        detailedAnimalSpecies =
                            customLayout.findViewById(R.id.detailedAnimalSpecies_textView)
                        detailedAnimalPreExistingIllness =
                            customLayout.findViewById(R.id.detailedAnimalPreExistingIllness_textView)
                        detailedAnimalColor =
                            customLayout.findViewById(R.id.detailedAnimalColor_textView)
                        detailedAnimalGender =
                            customLayout.findViewById(R.id.detailedAnimalGender_textView)
                        detailedShelterPhone =
                            customLayout.findViewById(R.id.detailedAnimalPhone_textView)
                        detailedShelterEmail =
                            customLayout.findViewById(R.id.detailedAnimalEmail_textView)
                        detailedShelterPhoneHint =
                            customLayout.findViewById(R.id.detailedAnimalPhoneHint_textView)
                        detailedShelterEmailHint =
                            customLayout.findViewById(R.id.detailedAnimalEmailHint_textView)
                        detailedAnimalDislikeButton = customLayout.findViewById(R.id.detailedAnimalDislike_imageButton)
    
    
    
    
                        detailedAnimalName.text = currentAnimal.name
                        currentAnimal.picture_one?.let { pictureOne ->
                            // Dekodiere das Bild aus dem Base64-String
                            val decodedBitmap = Base64Utils.decode(pictureOne)
        
                            val width = decodedBitmap.width
                            val height = decodedBitmap.height
        
                            val targetSize = height.coerceAtMost(width)
        
                            val x = (width - targetSize) / 2
                            val y = (height - targetSize) / 2
        
                            // Skaliere das Bild entsprechend der gewünschten Breite und Höhe
                            val scaledBitmap =
                                Bitmap.createBitmap(decodedBitmap, x, y, targetSize, targetSize)
                            // Oder:
                            // val scaledBitmap = BitmapScaler.scaleToFitHeight(decodedBitmap, screenHeight)
        
                            // Setze das skalierte Bild in das ImageView
                            detailedAnimalPicture.setImageBitmap(scaledBitmap)
                        }
                        detailedAnimalSpecies.text = currentAnimal.species
                        detailedAnimalBreed.text = currentAnimal.breed
                        detailedAnimalGender.text = currentAnimal.gender
                        detailedAnimalColor.text = currentAnimal.color
                        detailedAnimalPreExistingIllness.text = currentAnimal.illness
    
                        detailedAnimalBirthday.text = currentAnimal.birthday
    
                        detailedAnimalDislikeButton.visibility = View.GONE
    
                        detailedShelterEmail.text = resources.getString(R.string.like_to_see)
                        detailedShelterPhone.text = resources.getString(R.string.like_to_see)
    
    
                        val builder = AlertDialog.Builder(this)
                        builder.setView(customLayout)
    
                        val dialog: AlertDialog = builder.create()
    
    
                        dialog.show()
    
                        val outerLayout: ConstraintLayout? = dialog.findViewById(R.id.outer_layout)
                        outerLayout?.setOnClickListener {
                            dialog.dismiss()
                        }
                    }

                    isDragging = false
                    false
                }
                
                else -> false
            }

        }
        
        imageList = imageList + R.drawable.pixabay_cute_cat
        imageList = imageList + R.drawable.dislike
        imageList = imageList + R.drawable.love
        imageList = imageList + R.drawable.wf
        imageList = imageList + R.drawable.paw_swipe_splash_screen
        

    }
    
    private fun isCardStackEmpty(): Boolean {
        return adapter.itemCount == 0
    }

    /**
     * Inflates the options menu.
     *
     * @param menu The menu to be inflated.
     * @return Returns true if the menu is created successfully.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handles the selected menu item.
     *
     * @param item The selected menu item.
     * @return Returns true if the menu item is handled successfully.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intent: Intent =
                    if (AppData.getDiscriminator(this@MatchActivity) == "shelter") {
                        Intent(this@MatchActivity, EditShelterActivity::class.java)
                    } else Intent(this@MatchActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_animalServices -> {
                val intent = Intent(this@MatchActivity, AnimalServiceActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_animalEdit -> {
                val intent = Intent(this@MatchActivity, EditAnimalActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_animalCreate -> {
                val intent = Intent(this@MatchActivity, PetProfileActivity::class.java)
                intent.putExtra("id", profileId)
                startActivity(intent)
                true
            }

            R.id.menu_logOut -> {
                AppData.setID(this, 0)
                AppData.setPassword(this, "")
                AppData.setDiscriminator(this, "")
                val intent = Intent(this@MatchActivity, LoginActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_filter -> {
                val intent = Intent(this@MatchActivity, FilterActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Performs the like action for an animal.
     * Calls the corresponding API method based on the like status.
     *
     * @param profileId The profile ID of the user performing the action.
     * @param animalId The animal ID that is being liked or disliked.
     */
    private fun likeAnimal(profileId: Int, animalId: Int) {
        likeApi.likeAnimal(profileId, animalId) { response, error ->
            runOnUiThread {
                if (error != null) {
                    Toast.makeText(
                        this@MatchActivity, getString(R.string.like_error_text), Toast.LENGTH_SHORT
                    ).show()
                } else if (response?.isSuccessful == true) {
                    Log.i("PawSwipe", "Successfully liked")
                }
            }

        }
    }

    /** -------------------------------------------------------------------------------------- */
    private fun dislikeAnimal(profileId: Int, animalId: Int) {
        likeApi.dislikeAnimal(profileId, animalId) { response, error ->
            runOnUiThread {
                if (error != null) {
                    if (error.message != "Error: 500") {
                        Toast.makeText(
                            this@MatchActivity,
                            getString(R.string.like_error_text),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (response?.isSuccessful == true) {
                    Log.i("PawSwipe", "Succesfully Disliked")
                }
            }

        }
    }

    fun findLatLongForGivenAddress(address: String): Pair<Double, Double>? {
        return latLongUtil.getLatLongFromAddress(address)
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {

        if (currentPosition >= 0 && currentPosition < adapter.itemCount) {
            val animal = adapter.getAnimal(currentPosition)
            when (direction) {
                Direction.Left -> {
                    dislikeAnimal(profileId, animal.animal_id!!)
                    currentPosition++
                }
                
                Direction.Right -> {
                    likeAnimal(profileId, animal.animal_id!!)
                    currentPosition++
                }

                else -> {
                    // Swiped to the Wrong way
                }
            }

        }
    }

    override fun onCardRewound() {
        currentPosition--
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    private fun getAllAnimals() {

        var animals = mutableListOf<AnimalProfileData>()
        var filteredAnimalList =
            listOf<AnimalProfileData>() // converting of a list into mutable list failed in OutOfMemoryError due to the big size of the list

        val radius: Int = AppData.getRadius()
        val species: String = AppData.getSpecies()
        val illness: Boolean = AppData.getIllness()
        val breed: String = AppData.getBreed()
        val color: String = AppData.getColor()
        val gender: String = AppData.getGender()
        val minAge: String = AppData.getMinAge()
        val maxAge: String = AppData.getMaxAge()

        if (species == "" && illness && breed == "" && color == "" && gender == "" && minAge == "" && maxAge == "") {
            // No filter set so retriving all animals

            animalProfileApi.getAllAnimalProfileIDs { ids, error ->
                if (error != null) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MatchActivity,
                            "Ids konnten nicht geladen werden! (${error.message})",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                else {
                    ids?.forEach { int ->
                        animalProfileApi.getAnimalProfileByID(int) { profile, error ->
                            if (error != null) {
                                runOnUiThread {
                                    Toast.makeText(
                                        this@MatchActivity,
                                        "Tier konnte nicht geladen werden! (${error.message})",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                profile?.let {
                                    val animal = AnimalProfileData(
                                        it.animal_id,
                                        it.name,
                                        it.species,
                                        it.birthday,
                                        it.illness,
                                        it.description,
                                        it.breed,
                                        it.color,
                                        it.gender,
                                        it.picture_one,
                                        null,
                                        null,
                                        null,
                                        null,
                                        it.profile_id
                                    )
                                    animals.add(animal)
                                    // Check if all animals have been retrieved
                                }
                                if (animals.size == ids.size) {
                                    // All animals have been retrieved, initialize the adapter
                                    runOnUiThread {
                                        adapter = CardAdapter(animals.shuffled())
                                        cardStackView.adapter =
                                            adapter // Set the adapter for the cardStackView
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Retrieving animals by filter (radius needs to be checked locally) @todo ???

            val filters = mutableMapOf<FilterEnum, String>()
            val map = mutableMapOf<FilterEnum, String>()

            if (species != "") {
                filters[FilterEnum.SPECIES] = species
            }
            if (!illness) {
                filters[FilterEnum.ILLNESS] = ""
            }
            if (breed != "") {
                filters[FilterEnum.BREED] = breed
            }
            if (color != "") {
                filters[FilterEnum.COLOR] = color
            }
            if (gender != "") {
                filters[FilterEnum.GENDER] = gender
            }
            if (minAge != "") {
                filters[FilterEnum.AGE_MIN] = minAge
            }
            if (maxAge != "") {
                filters[FilterEnum.AGE_MAX] = maxAge
            }

            // Assign the filters to the 'map' parameter
            map.putAll(filters)

            FilterApi().filterAnimalsAndGetIds(map){ ids, error ->
                if(error != null){
                    runOnUiThread {
                        Toast.makeText(this@MatchActivity, error.message, Toast.LENGTH_SHORT).show()
                    }
                }else {
                    Log.d("Filter", ids.toString())


                    ids?.forEach { int ->
                        animalProfileApi.getAnimalProfileByID(int) { profile, error ->
                            if (error != null) {
                                runOnUiThread {
                                    Toast.makeText(
                                        this@MatchActivity,
                                        "Tier konnte nicht geladen werden! (${error.message})",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                profile?.let {
                                    val animal = AnimalProfileData(
                                        it.animal_id,
                                        it.name,
                                        it.species,
                                        it.birthday,
                                        it.illness,
                                        it.description,
                                        it.breed,
                                        it.color,
                                        it.gender,
                                        it.picture_one,
                                        null,
                                        null,
                                        null,
                                        null,
                                        it.profile_id
                                    )
                                    animals.add(animal)
                                    // Check if all animals have been retrieved
                                }
                                if (animals.size == ids.size) {
                                    // All animals have been retrieved, initialize the adapter
                                    runOnUiThread {
                                        adapter = CardAdapter(animals.shuffled())
                                        cardStackView.adapter =
                                            adapter // Set the adapter for the cardStackView
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        adapter = CardAdapter(animals)
        cardStackView.adapter = adapter // Setzen Sie den Adapter für den CardStackView
    }
}


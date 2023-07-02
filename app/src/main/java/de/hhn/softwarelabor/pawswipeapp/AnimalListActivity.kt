package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hhn.softwarelabor.pawswipeapp.api.animal.AnimalProfileApi
import de.hhn.softwarelabor.pawswipeapp.api.like.LikeApi
import de.hhn.softwarelabor.pawswipeapp.utils.AnimalAdapter
import de.hhn.softwarelabor.pawswipeapp.utils.AnimalItem
import de.hhn.softwarelabor.pawswipeapp.utils.AppData
import de.hhn.softwarelabor.pawswipeapp.utils.Base64Utils
import de.hhn.softwarelabor.pawswipeapp.utils.BitmapScaler


/**
 * AnimalListActivity is an AppCompatActivity that displays a list of liked animals.
 * @author Felix Kuhbier & Leo Kalmbach
 *
 */

private const val BITMAP_WIDTH = 250
private const val BITMAP_HEIGHT = 250
private const val BITMAP_WIDTH_DETAILED = 750
private const val BITMAP_HEIGHT_DETAILED = 750

private const val DISCRIMINATOR_SHELTER = "shelter"

class AnimalListActivity : AppCompatActivity() {

    private lateinit var matchBtn: Button
    private lateinit var filterBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var animalAdapter: AnimalAdapter
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


    private var animalItems: ArrayList<AnimalItem> = ArrayList()
    private var profileId: Int = 0

    private var backPressedOnce = false
    private val timerDuration = 3000 // 3 Sekunden
    private val likeApi: LikeApi = LikeApi()
    private val animalProfileApi: AnimalProfileApi = AnimalProfileApi()

    /**
     * Initializes the activity, sets up the UI and loads the liked animals.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_list)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        matchBtn = findViewById(R.id.matching_btn3)
        filterBtn = findViewById(R.id.filter_btn3)
        recyclerView = findViewById(R.id.animal_list_recyclerView)


        profileId = AppData.getID(this)

        //animal profile details dialog
        val animalItemClick: (AnimalItem) -> Unit = { item ->
            val customLayout =
                layoutInflater.inflate(R.layout.activity_detailed_animal_profile, null)


            detailedAnimalName = customLayout.findViewById(R.id.detailedAnimalName_textView)
            detailedAnimalPicture = customLayout.findViewById(R.id.detailedAnimalPicture_imageView)
            detailedAnimalBreed = customLayout.findViewById(R.id.detailedAnimalBreed_textView)
            detailedAnimalBirthday = customLayout.findViewById(R.id.detailedAnimalBirthday_textView)
            detailedAnimalSpecies = customLayout.findViewById(R.id.detailedAnimalSpecies_textView)
            detailedAnimalPreExistingIllness =
                customLayout.findViewById(R.id.detailedAnimalPreExistingIllness_textView)
            detailedAnimalColor = customLayout.findViewById(R.id.detailedAnimalColor_textView)
            detailedAnimalGender = customLayout.findViewById(R.id.detailedAnimalGender_textView)
            detailedShelterPhone = customLayout.findViewById(R.id.detailedAnimalPhone_textView)
            detailedShelterEmail = customLayout.findViewById(R.id.detailedAnimalEmail_textView)
            detailedShelterPhoneHint =
                customLayout.findViewById(R.id.detailedAnimalPhoneHint_textView)
            detailedShelterEmailHint =
                customLayout.findViewById(R.id.detailedAnimalEmailHint_textView)
            detailedAnimalDislikeButton =
                customLayout.findViewById(R.id.detailedAnimalDislike_imageButton)




            detailedAnimalName.text = item.animalName
            detailedAnimalPicture.setImageBitmap(item.imageResId?.let {
                BitmapScaler.scaleToFitWidth(it, BITMAP_WIDTH_DETAILED)
                BitmapScaler.scaleToFitHeight(it, BITMAP_HEIGHT_DETAILED)
            })
            detailedAnimalSpecies.text = item.animalSpecies
            detailedAnimalBreed.text = item.animalBreed
            detailedAnimalGender.text = item.animalGender
            detailedAnimalColor.text = item.animalColor
            detailedAnimalPreExistingIllness.text = item.animalPreExistingIllness

            detailedAnimalBirthday.text = item.animalBirthday

            //when discriminator == shelter -> hide phone and email
            if (AppData.getDiscriminator(this) == DISCRIMINATOR_SHELTER) {
                detailedShelterPhone.visibility = View.GONE
                detailedShelterPhoneHint.visibility = View.GONE
                detailedShelterEmail.visibility = View.GONE
                detailedShelterEmailHint.visibility = View.GONE
                detailedAnimalDislikeButton.visibility = View.GONE
            } else {
                detailedShelterEmail.text = item.shelterEmail
                detailedShelterPhone.text =
                    getString(R.string.detailed_shelter_phone_text, item.shelterPhone)
            }


            val builder = AlertDialog.Builder(this)
            builder.setView(customLayout)

            val dialog: AlertDialog = builder.create()


            dialog.show()

            val outerLayout: ConstraintLayout? = dialog.findViewById(R.id.outer_layout)
            outerLayout?.setOnClickListener {
                dialog.dismiss()
            }

            detailedAnimalDislikeButton.setOnClickListener {
                item.animalID?.let { it1 ->
                    LikeApi().dislikeAnimal(profileId, it1) { _, error ->
                        if (error != null) {
                            runOnUiThread {
                                Toast.makeText(
                                    this@AnimalListActivity,
                                    getString(R.string.like_error_text),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this@AnimalListActivity,
                                    "Tier wurde als \"Gefällt mir nicht\" markiert",
                                    Toast.LENGTH_SHORT
                                ).show()

                                dialog.dismiss()
                                reloadActivity()

                            }


                        }
                    }
                }


            }
        }

        animalAdapter = AnimalAdapter(animalItems, animalItemClick, this@AnimalListActivity)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = animalAdapter



        onBackPressedDispatcher.addCallback(
            this /* lifecycle owner */,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressedOnce) {
                        finishAffinity(this@AnimalListActivity) // Beendet alle Activities und die App
                        return
                    }

                    backPressedOnce = true
                    Toast.makeText(
                        this@AnimalListActivity,
                        getString(R.string.zum_beenden_der_app),
                        Toast.LENGTH_SHORT
                    ).show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        backPressedOnce = false
                    }, timerDuration.toLong())
                }
            }
        )





        if (AppData.getDiscriminator(this@AnimalListActivity) == DISCRIMINATOR_SHELTER) {
            getAnimalsFromShelter(profileId)
            updateEmptyTextViewVisibility(getString(R.string.shelter_no_animals_text))
            matchBtn.visibility = View.GONE
        } else {
            getAnimalItemsFromAnimalIds(profileId)
            updateEmptyTextViewVisibility(getString(R.string.profile_no_animals_liked_text))
            matchBtn.setOnClickListener {
                val intent = Intent(this@AnimalListActivity, MatchActivity::class.java)
                intent.putExtra("id", profileId)
                startActivity(intent)
            }

        }


        filterBtn.setOnClickListener {
            val intent = Intent(this@AnimalListActivity, FilterActivity::class.java)
            startActivity(intent)
        }

    }


    /**
     * This function is used to reload the current activity.
     *
     * It first finishes the current activity with [finish], and then restarts
     * the same activity with [startActivity]. [overridePendingTransition] is used
     * to remove the transition animations, providing a seamless reload effect.
     */
    private fun reloadActivity() {
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    /**
     * Inflates the options menu.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handles the selected option from the options menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intent: Intent =
                    if (AppData.getDiscriminator(this@AnimalListActivity) == DISCRIMINATOR_SHELTER) {
                        Intent(this@AnimalListActivity, EditShelterActivity::class.java)
                    } else
                        Intent(this@AnimalListActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_animalServices -> {
                val intent = Intent(this@AnimalListActivity, AnimalServiceActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_animalEdit -> {
                val intent = Intent(this@AnimalListActivity, EditAnimalActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_animalCreate -> {
                val intent = Intent(this@AnimalListActivity, PetProfileActivity::class.java)
                intent.putExtra("id", profileId)
                startActivity(intent)
                true
            }

            R.id.menu_logOut -> {
                AppData.setID(this, 0)
                AppData.setPassword(this, "")
                AppData.setDiscriminator(this, "")
                val intent = Intent(this@AnimalListActivity, LoginActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_filter -> {
                val intent = Intent(this@AnimalListActivity, FilterActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Updates the visibility of the emptyTextView based on the size of the animalItems ArrayList.
     */
    private fun updateEmptyTextViewVisibility(message: String) {
        val emptyTextView: TextView = findViewById(R.id.animal_list_empty_textView)
        emptyTextView.visibility = if (animalItems.isEmpty()) View.VISIBLE else View.GONE
        emptyTextView.text = message
    }

    /**
     * Retrieves AnimalItem objects based on the liked animal IDs for the given profileId.
     *
     * @param profileId Integer representing the profile ID of the current user.
     */
    private fun getAnimalItemsFromAnimalIds(profileId: Int) {


        //get all the IDs of animals you like
        likeApi.getLikedAnimals(profileId) { response, error ->
            if (error != null) {
                runOnUiThread {
                    Toast.makeText(
                        this@AnimalListActivity,
                        "Tiere konnten nicht geladen werden",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                response?.forEach { id ->

                    //Get profiles from IDs and add them to the list
                    animalProfileApi.getAnimalProfileByID(id) { response, error ->

                        runOnUiThread {
                            if (error != null) {

                                Toast.makeText(
                                    this@AnimalListActivity,
                                    "${error.message}",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {


                                val item = AnimalItem(
                                    response?.animal_id,
                                    response?.picture_one?.let {
                                        BitmapScaler.scaleToFitWidth(
                                            Base64Utils.decode(it),
                                            BITMAP_WIDTH
                                        )

                                        BitmapScaler.scaleToFitHeight(
                                            Base64Utils.decode(it),
                                            BITMAP_HEIGHT
                                        )
                                    },
                                    response?.name,
                                    response?.species,
                                    response?.breed,
                                    response?.birthday,
                                    response?.illness,
                                    if (response?.color.isNullOrEmpty()) "n.a." else response?.color,
                                    response?.gender,
                                    response?.description,
                                    response?.profile_id?.phone_number,
                                    response?.profile_id?.email

                                )
                                animalAdapter.addItem(item)

                                updateEmptyTextViewVisibility(getString(R.string.profile_no_animals_liked_text))

                            }
                        }
                    }

                }
            }
        }
    }

    private fun getAnimalsFromShelter(profileId: Int) {

        animalProfileApi.getAllAnimalProfileIDs { ints, error ->

            if (error != null) {
                runOnUiThread {
                    Toast.makeText(
                        this@AnimalListActivity,
                        "Ids konnten nicht geladen werden! (${error.message})",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {


                ints?.forEach { int ->
                    animalProfileApi.getAnimalProfileByID(int) { profile, error ->
                        if (error != null) {
                            runOnUiThread {
                                Toast.makeText(
                                    this@AnimalListActivity,
                                    "Tier konnte nicht geladen werden! (${error.message})",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            runOnUiThread {
                                if (profile?.profile_id?.profile_id == profileId) {
                                    val item = AnimalItem(
                                        profile.animal_id,
                                        profile.picture_one?.let {
                                            BitmapScaler.scaleToFitWidth(
                                                Base64Utils.decode(it),
                                                BITMAP_WIDTH
                                            )

                                            BitmapScaler.scaleToFitHeight(
                                                Base64Utils.decode(it),
                                                BITMAP_HEIGHT
                                            )
                                        },
                                        profile.name,
                                        profile.species,
                                        profile.breed,
                                        profile.birthday,
                                        profile.illness,
                                        if (profile.color.isNullOrEmpty()) "n.a." else profile.color,
                                        profile.gender,
                                        profile.description,
                                        null,
                                        null
                                    )
                                    animalAdapter.addItem(item)

                                    updateEmptyTextViewVisibility(getString(R.string.shelter_no_animals_text))
                                }
                            }

                        }


                    }

                }


            }

        }


    }
}





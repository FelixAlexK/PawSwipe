package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import de.hhn.softwarelabor.pawswipeapp.api.like.LikeApi
import de.hhn.softwarelabor.pawswipeapp.utils.AppData
import de.hhn.softwarelabor.pawswipeapp.utils.ViewPagerAdapter

/**
 * Match activity is an activity for displaying and interacting with animal profiles.
 * @author Felix Kuhbier & Leo Kalmbach
 * @since 2023.06.12
 */
class MatchActivity : AppCompatActivity() {
    
    private lateinit var chatBtn: Button
    private lateinit var animalListBtn: Button
    private lateinit var likeBtn: ImageButton
    private lateinit var dislikeBtn: ImageButton
    private lateinit var matchBtn: Button
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var imageList: List<Int>
    
    private var profileId: Int = 0
    private var likeApi: LikeApi = LikeApi()
    private var animalId: Int = 0
    
    private var backPressedOnce = false
    private val timerDuration = 3000 // 3 Sekunden
    
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        
        if (backPressedOnce) {
            finishAffinity()    // Beendet alle Activities und die App
            return
        }
        
        backPressedOnce = true
        Toast.makeText(
            this,
            getString(R.string.zum_beenden_der_app),
            Toast.LENGTH_SHORT
        ).show()
        
        Handler(Looper.getMainLooper()).postDelayed({
            backPressedOnce = false
        }, timerDuration.toLong())
        
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        
        // If a bug brings a Shelter to the Match Activity, it closes the Activity
        if (AppData.getDiscriminator(this@MatchActivity) == "shelter"){
            finish()
        }

        
        profileId = AppData.getID(this)
        animalId = intent.getIntExtra("animal_id", 0)
        
        chatBtn = findViewById(R.id.chat_btn2)
        animalListBtn = findViewById(R.id.animalList_btn2)
        likeBtn = findViewById(R.id.like_button)
        dislikeBtn = findViewById(R.id.dislike_button)
        matchBtn = findViewById(R.id.matching_btn2)
        viewPager = findViewById(R.id.idViewPager)
        imageList = ArrayList()
    
        if(AppData.getDiscriminator(this@MatchActivity) == "shelter"){
            matchBtn.isClickable = false
            matchBtn.setBackgroundColor(Color.TRANSPARENT)
            matchBtn.background = null
        }
        
        chatBtn.setOnClickListener {
            val intent = Intent(this@MatchActivity, ChatActivity::class.java)
            startActivity(intent)
        }
        
        animalListBtn.setOnClickListener {
            val intent = Intent(this@MatchActivity, AnimalListActivity::class.java)
            intent.putExtra("id", profileId)
            startActivity(intent)
        }
        
        likeBtn.setOnClickListener {
            
            likeAnimal(profileId, animalId)
            
        }
        
        dislikeBtn.setOnClickListener {
            //TODO(Swipe action on click)
        }
        
        imageList = imageList + R.drawable.pixabay_cute_cat
        imageList = imageList + R.drawable.dislike
        imageList = imageList + R.drawable.love
        imageList = imageList + R.drawable.wf
        imageList = imageList + R.drawable.paw_swipe_splash_screen
        
        viewPagerAdapter = ViewPagerAdapter(this@MatchActivity, imageList)
        
        viewPager.adapter = viewPagerAdapter
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
                val intent: Intent = if (AppData.getDiscriminator(this@MatchActivity) == "shelter"){
                    Intent(this@MatchActivity, EditShelterActivity::class.java)
                } else
                    Intent(this@MatchActivity, SettingsActivity::class.java)
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
                        this@MatchActivity,
                        getString(R.string.like_error_text),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (response?.isSuccessful == true) {
                    Log.i("PawSwipe", "Successfully liked")
                }
            }
            
        }
        
    }
}


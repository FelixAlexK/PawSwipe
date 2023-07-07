package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.utils.AppData
/**
 * The AnimalServiceActivity class represents an activity for animal services.
 */
class AnimalServiceActivity : AppCompatActivity() {

    private lateinit var chatBtn: Button
    private lateinit var matchBtn: Button
    private lateinit var animalListBtn: Button

    /**
     * Creates the activity and initializes its views.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_service)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        chatBtn = findViewById(R.id.chat_btn4)
        chatBtn.setOnClickListener {
            val intent = Intent(this@AnimalServiceActivity, ChatActivity::class.java)
            startActivity(intent)
        }
        matchBtn = findViewById(R.id.matching_btn4)
        if(AppData.getDiscriminator(this@AnimalServiceActivity) == "shelter"){
            matchBtn.isClickable = false
            matchBtn.setBackgroundColor(Color.TRANSPARENT)
            matchBtn.background = null
        } else {
            matchBtn.setOnClickListener {
                val intent = Intent(this@AnimalServiceActivity, MatchActivity::class.java)
                startActivity(intent)
            }
        
        }
        animalListBtn = findViewById(R.id.animalList_btn4)
        animalListBtn.setOnClickListener {
            val intent = Intent(this@AnimalServiceActivity, AnimalListActivity::class.java)
            startActivity(intent)
        }
    }
    /**
     * Creates the options menu for the activity.
     *
     * @param menu The menu to inflate.
     * @return true to display the menu, false otherwise.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (AppData.getDiscriminator(this@AnimalServiceActivity) == "shelter") {
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_home, menu)
        } else {
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_user, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    /**
     * Handles the selection of an option from the menu.
     *
     * @param item The selected menu item.
     * @return true if the item is handled, false otherwise.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intent: Intent = if (AppData.getDiscriminator(this@AnimalServiceActivity) == "shelter"){
                    Intent(this@AnimalServiceActivity, EditShelterActivity::class.java)
                } else
                    Intent(this@AnimalServiceActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalEdit -> {
                val intent = Intent(this@AnimalServiceActivity, EditAnimalActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalCreate -> {
                val intent = Intent(this@AnimalServiceActivity, PetProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_logOut -> {
                AppData.setID(this, 0)
                AppData.setPassword(this, "")
                AppData.setDiscriminator(this, "")
                val intent = Intent(this@AnimalServiceActivity, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_filter -> {
                val intent = Intent(this@AnimalServiceActivity, FilterActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
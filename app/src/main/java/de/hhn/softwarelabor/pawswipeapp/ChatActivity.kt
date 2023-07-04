package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.utils.AppData

class ChatActivity : AppCompatActivity() {

    private lateinit var matchBtn: Button
    private lateinit var animalListBtn: Button
    
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
        setContentView(R.layout.activity_chat)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        matchBtn = findViewById(R.id.matching_btn)
    
        if(AppData.getDiscriminator(this@ChatActivity) == "shelter"){
            matchBtn.isClickable = false
            matchBtn.setBackgroundColor(Color.TRANSPARENT)
            matchBtn.background = null
        } else {
            matchBtn.setOnClickListener {
                val intent = Intent(this@ChatActivity, MatchActivity::class.java)
                startActivity(intent)
            }
        
        }
        

        animalListBtn = findViewById(R.id.animalList_btn)
        animalListBtn.setOnClickListener {
            val intent = Intent(this@ChatActivity, AnimalListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (AppData.getDiscriminator(this@ChatActivity) == "shelter") {
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_home,menu)
        } else {
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_user,menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intent: Intent = if (AppData.getDiscriminator(this@ChatActivity) == "shelter"){
                    Intent(this@ChatActivity, EditShelterActivity::class.java)
                } else
                    Intent(this@ChatActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalServices -> {
                val intent = Intent(this@ChatActivity, AnimalServiceActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalEdit -> {
                val intent = Intent(this@ChatActivity, EditAnimalActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_animalCreate -> {
                val intent = Intent(this@ChatActivity, PetProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_logOut -> {
                AppData.setID(this, 0)
                AppData.setPassword(this, "")
                AppData.setDiscriminator(this, "")
                val intent = Intent(this@ChatActivity, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_filter -> {
                val intent = Intent(this@ChatActivity, FilterActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
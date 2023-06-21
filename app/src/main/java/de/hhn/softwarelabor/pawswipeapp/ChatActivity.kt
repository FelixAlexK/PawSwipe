package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
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
        matchBtn.setOnClickListener {
            val intent = Intent(this@ChatActivity, MatchActivity::class.java)
            startActivity(intent)
        }

        animalListBtn = findViewById(R.id.animalList_btn)
        animalListBtn.setOnClickListener {
            val intent = Intent(this@ChatActivity, AnimalListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intent = Intent(this@ChatActivity, SettingsActivity::class.java)
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
package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AnimalListActivityNico : AppCompatActivity() {

    private lateinit var matchBtn: Button
    private lateinit var chatBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_list)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        matchBtn = findViewById(R.id.matching_btn3)
        matchBtn.setOnClickListener {
            val intent = Intent(this@AnimalListActivityNico, MatchActivity::class.java)
            startActivity(intent)
        }

        chatBtn = findViewById(R.id.chat_btn3)
        chatBtn.setOnClickListener {
            val intent = Intent(this@AnimalListActivityNico, ChatActivity::class.java)
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
                val intent = Intent(this@AnimalListActivityNico, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalServices -> {
                val intent = Intent(this@AnimalListActivityNico, AnimalServiceActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalEdit -> {
                val intent = Intent(this@AnimalListActivityNico, EditAnimalActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalCreate -> {
                val intent = Intent(this@AnimalListActivityNico, PetProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_logOut -> {
                val intent = Intent(this@AnimalListActivityNico, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_filter -> {
                val intent = Intent(this@AnimalListActivityNico, FilterActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
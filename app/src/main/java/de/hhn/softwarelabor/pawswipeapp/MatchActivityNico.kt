package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button

class MatchActivityNico : AppCompatActivity() {

    private lateinit var chatBtn: Button
    private lateinit var animalListBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        chatBtn = findViewById(R.id.chat_btn2)
        chatBtn.setOnClickListener {
            val intent = Intent(this@MatchActivityNico, ChatActivity::class.java)
            startActivity(intent)
        }

        animalListBtn = findViewById(R.id.animalList_btn2)
        animalListBtn.setOnClickListener {
            val intent = Intent(this@MatchActivityNico, AnimalListActivityNico::class.java)
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
                val intent = Intent(this@MatchActivityNico, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalServices -> {
                val intent = Intent(this@MatchActivityNico, AnimalServiceActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalEdit -> {
                val intent = Intent(this@MatchActivityNico, EditAnimalActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
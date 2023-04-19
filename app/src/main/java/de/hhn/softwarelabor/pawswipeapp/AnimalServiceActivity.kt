package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button

class AnimalServiceActivity : AppCompatActivity() {

    private lateinit var chatBtn: Button
    private lateinit var matchBtn: Button
    private lateinit var animalListBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_service)

        chatBtn = findViewById(R.id.chat_btn4)
        chatBtn.setOnClickListener {
            val intent = Intent(this@AnimalServiceActivity, ChatActivity::class.java)
            startActivity(intent)
        }
        matchBtn = findViewById(R.id.matching_btn4)
        matchBtn.setOnClickListener {
            val intent = Intent(this@AnimalServiceActivity, MatchActivityNico::class.java)
            startActivity(intent)
        }
        animalListBtn = findViewById(R.id.animalList_btn4)
        animalListBtn.setOnClickListener {
            val intent = Intent(this@AnimalServiceActivity, AnimalListActivityNico::class.java)
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
                val intent = Intent(this@AnimalServiceActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalEdit -> {
                val intent = Intent(this@AnimalServiceActivity, EditAnimalActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
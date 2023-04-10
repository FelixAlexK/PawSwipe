package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class SettingsActivity : AppCompatActivity() {

    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        cancelButton = findViewById(R.id.cancel_btn)
        cancelButton.setOnClickListener {
            val intent = Intent(this@SettingsActivity, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}
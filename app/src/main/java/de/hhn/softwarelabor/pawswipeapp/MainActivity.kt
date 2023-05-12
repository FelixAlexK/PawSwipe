package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Inside the onClick method of a button or any other event listener
        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)

    }
}
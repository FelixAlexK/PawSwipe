package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * NUR fürs schnelle Testen, started in meiner Register Activity
         */
        val intent = Intent(this, RegisterAccountActivity::class.java)
        startActivity(intent)

    }
}
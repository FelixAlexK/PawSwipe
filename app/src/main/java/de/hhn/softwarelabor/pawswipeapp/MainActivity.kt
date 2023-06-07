package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    /** Duration of wait **/
    private val SPLASH_DISPLAY_LENGTH = 1700

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Splash screen
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())

    }


}
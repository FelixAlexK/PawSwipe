package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

/**
 * This activity displays the PawSwipe intro animation (also called a "Splash Screen")
 * when starting the app cold (without it being open in background processes).
 * It displays for one cycle.
 */
class MainActivity : AppCompatActivity() {

    /** Duration of wait **/
    /** One cycle is roughly 1700ms **/
    private val SPLASH_DISPLAY_LENGTH = 1700

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        //Splash screen
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())


    }


}
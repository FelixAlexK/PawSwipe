package de.hhn.softwarelabor.pawswipeapp

import android.content.ContentValues.TAG
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi

private const val DISCRIMINATOR_SHELTER = "shelter"
private const val DISCRIMINATOR_PROFILE = "profile"
class LoginActivity : AppCompatActivity() {
    private lateinit var loginUserButton: Button
    private lateinit var loginShelterButton: Button
    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    private lateinit var loginRegisterButton: Button
    private lateinit var loginLoginButton: Button

    private var isShelter = false
    private var isUser = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()

        loginUserButton.setOnClickListener {
            try {
                loginUserButton.setBackgroundColor(Color.GREEN)
                loginShelterButton.setBackgroundColor(resources.getColor(R.color.pawswipe_orange_700, null))
            }catch (e: Resources.NotFoundException){
                Log.e(TAG, e.message.toString())
                e.printStackTrace()
            }

            isUser = true
            isShelter = false
        }

        loginShelterButton.setOnClickListener {
            try {
                loginShelterButton.setBackgroundColor(Color.GREEN)
                loginUserButton.setBackgroundColor(resources.getColor(R.color.pawswipe_orange_700,null))
            }catch (e: Resources.NotFoundException){
                Log.e(TAG, e.message.toString())
                e.printStackTrace()
            }

            isShelter = true
            isUser = false
        }

        loginLoginButton.setOnClickListener {
            val email: String = loginEmailEditText.text.toString()
            val password: String = loginPasswordEditText.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                if(isShelter){
                    loginShelter(email, password)
                }else if(isUser){
                    //loginUser(email, password)
                }else {
                    runOnUiThread{
                        Toast.makeText(this, "Bitte wählen Sie aus, ob Sie sich als Nutzer oder Tierheim anmelden möchten.", Toast.LENGTH_SHORT).show()
                    }
                }

            }else {
                runOnUiThread{
                    Toast.makeText(this, "Bitte gib eine gültige E-Mail-Adresse und ein Passwort ein.", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    private fun loginShelter(email: String, password: String) {
        val profile = ProfileApi()

        profile.getUserProfileByEmail(email){ shelter, error ->
            if(error != null){
                runOnUiThread{
                    Toast.makeText(this,"Es wurde kein Account mit dieser E-Mail-Adresse gefunden. Bitte überprüfen Sie Ihre Eingabe und versuchen Sie es erneut.",Toast.LENGTH_SHORT).show()
                }
                Log.e(TAG, error.message.toString())
            }else if(shelter != null && shelter.discriminator.equals(DISCRIMINATOR_SHELTER, ignoreCase = true)){
                if (password == shelter.password){
                    runOnUiThread{
                        Toast.makeText(this,"Willkommen zurück, ${shelter.username}! Sie haben sich erfolgreich angemeldet.",Toast.LENGTH_SHORT).show()
                    }
                }else {
                    runOnUiThread{
                        Toast.makeText(this,"Login fehlgeschlagen. Das eingegebene Passwort ist falsch. Bitte versuchen Sie es erneut.",Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                runOnUiThread{
                    Toast.makeText(this,"Es wurde kein Tierheim-Profil mit dieser E-Mail-Adresse gefunden. Bitte überprüfen Sie Ihre Eingabe und versuchen Sie es erneut.",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun init(){
        try{
            loginUserButton = findViewById(R.id.loginUser_button)
            loginShelterButton = findViewById(R.id.loginShelter_button)
            loginEmailEditText = findViewById(R.id.loginEmail_editText)
            loginPasswordEditText = findViewById(R.id.loginPassword_editText)
            loginRegisterButton = findViewById(R.id.loginRegister_button)
            loginLoginButton = findViewById(R.id.loginLogin_button)
        }catch (e: NullPointerException){
            Log.e(TAG, e.message.toString())
            e.printStackTrace()
        }
    }
}
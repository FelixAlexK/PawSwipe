package de.hhn.softwarelabor.pawswipeapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class RegisterUserAccountActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        val gotoUserButton = findViewById<Button>(R.id.gotoUserButton)
        val gotoAnimalHomeButton = findViewById<Button>(R.id.gotoAnimalHomeButton)
        val gotoRegisterButton = findViewById<Button>(R.id.gotoRegisterButton)
        val backToLoginButton = findViewById<Button>(R.id.backToLoginButton)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val emailInputString = emailInput.text.toString()
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val passwordInputString = passwordInput.text.toString()
        val passwordConfirmInput = findViewById<EditText>(R.id.passwordConfirmInput)
        val passwordConfirmInputString = passwordConfirmInput.text.toString()





        gotoRegisterButton.setOnClickListener {
            // Simons name for the class: CreateShelterActivity & CreateUserProfileActivity
            if (passwordConfirmInputString.equals(passwordInput)) {
                /*
                try {
                    val intent = Intent(it.context, CreateUserProfileActivity::class.java)
                    // not yet initialized
                    intent.putExtra("emailInputString", emailInputString)
                    intent.putExtra("passwordInputString", passwordInputString)
                    startActivity(intent)
                } catch (e: java.lang.Exception) {
                    throw Exception("Etwas ist schiefgelaufen.  \n " +
                        "Ueberpruefe deine Verbindung \n" +
                        " oder versuche es später noch einmal")
                }
                 */
            } else {
                throw Exception("Passwoerter stimmen nicht ueberein")
            }
        }

        backToLoginButton.setOnClickListener {
            /*
            try {
                val intent = Intent(it.context, UserLoginActivity::class.java)
                startActivity(intent)
            } catch(e: java.lang.Exception) {
                throw Exception("Etwas ist schiefgelaufen.  \n " +
                        "Ueberpruefe deine Verbindung \n" +
                        " oder versuche es später noch einmal")
            }
        }
        */
        }

        gotoAnimalHomeButton.setOnClickListener {
            /*
            try {
                val intent = Intent(it.context, RegisterShelterAccountActivity::class.java)
                startActivity(intent)
            } catch (e: java.lang.Exception) {
                throw Exception("Etwas ist schiefgelaufen.  \n " +
                        "Ueberpruefe deine Verbindung \n" +
                        " oder versuche es später noch einmal")
            }
             */
        }


    }
}
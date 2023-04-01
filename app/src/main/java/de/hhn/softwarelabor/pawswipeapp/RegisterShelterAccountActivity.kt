package de.hhn.softwarelabor.pawswipeapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class RegisterShelterAccountActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_shelter_account)

        val gotoUserButton = findViewById<Button>(R.id.gotoUserButton)
        val gotoAnimalHomeButton = findViewById<Button>(R.id.gotoAnimalHomeButton)
        val gotoRegisterButton = findViewById<Button>(R.id.gotoRegisterButton)
        val backToLoginButton = findViewById<Button>(R.id.backToLoginButton)

        val errorTextView = findViewById<TextView>(R.id.errorTextView)

        // checkInput()
        /*
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val emailInputString = emailInput.text.toString().trim()
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val passwordInputString = passwordInput.text.toString().trim()
        val passwordConfirmInput = findViewById<EditText>(R.id.passwordConfirmInput)
        val passwordConfirmInputString = passwordConfirmInput.text.toString().trim()
        */

        // CreateShelterActivity, CreateUserProfileActivity, UserLoginActivity

        /*
        gotoRegisterButton.setOnClickListener {
            // Simons name for the class: CreateShelterActivity & CreateUserProfileActivity
            if (passwordConfirmInputString.equals(passwordInput)) {
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
            } else {
                throw Exception("Passwoerter stimmen nicht ueberein")
            }
        }
         */

        gotoRegisterButton.setOnClickListener {
            checkInput()
            // just trying to get this compiled
        }

        backToLoginButton.setOnClickListener {
            /*
           try {
               val intent = Intent(it.context, UserLoginActivity::class.java)
               startActivity(intent)
           } catch(e: java.lang.Exception) {
                displayErrorMessage()
           }
            */
        }

        gotoAnimalHomeButton.setOnClickListener {
            // does nothing because we are already in its Activity
        }

        gotoUserButton.setOnClickListener {
            try {
                val intent = Intent(it.context, RegisterUserAccountActivity::class.java)
                startActivity(intent)
            } catch (e: java.lang.Exception) {
                displayNetworkErrorMessage()
            }
        }



    }

    /**
     * When trying to return to Activities where no sensitive data
     * is being send to, such as back to Login.
     */
    private fun displayNetworkErrorMessage() {
        val errorTextView = findViewById<TextView>(R.id.errorTextView)
        errorTextView.setText("Etwas ist schiefgelaufen.  \n " +
                "Ueberpruefe deine Verbindung \n" +
                " oder versuche es später noch einmal")
        errorTextView.setVisibility(View.VISIBLE)
    }

    private fun checkInput() {
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val emailInputString = emailInput.text.toString().trim()

        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val passwordInputString = passwordInput.text.toString().trim()

        val passwordConfirmInput = findViewById<EditText>(R.id.passwordConfirmInput)
        val passwordConfirmInputString = passwordConfirmInput.text.toString().trim()

        if (emailInputString.isEmpty()
            || passwordInputString.isEmpty()
            || passwordConfirmInputString.isEmpty()) {
            // throw java.lang.IllegalArgumentException("")
            displayNetworkErrorMessage()
        }
    }
    /*
    private fun registerUser() {
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val emailInputString = emailInput.text.toString().trim()
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val passwordInputString = passwordInput.text.toString().trim()
        val passwordConfirmInput = findViewById<EditText>(R.id.passwordConfirmInput)
        val passwordConfirmInputString = passwordConfirmInput.text.toString().trim()

        // Encrypt sensitive user data before sending to backend
        val encryptedEmail = encrypt(emailInputString)
        val encryptedPassword = encrypt(passwordInputString)

        // Make API call to register user
        val call = apiService.registerUser(name, encryptedEmail, encryptedPassword)
        call.enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                if (response.isSuccessful) {
                    val registrationResponse = response.body()
                    // Handle successful registration response
                } else {
                    // Handle registration error
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                // Handle API call failure
            }
        })
    }
    */

    /**
     * Encrypts
     */
    /*
    private fun encrypt(text: String): String {
        val keyGenerator = KeyGenerator.getInstance("AES")
        val secureRandom = SecureRandom.getInstanceStrong()
        keyGenerator.init(256, secureRandom)
        val secretKey: SecretKey = keyGenerator.generateKey()

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val encryptedBytes = cipher.doFinal(text.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }
     */
}
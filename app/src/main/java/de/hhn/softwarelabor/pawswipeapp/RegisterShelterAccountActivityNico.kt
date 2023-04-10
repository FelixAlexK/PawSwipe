package de.hhn.softwarelabor.pawswipeapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

class RegisterShelterAccountActivityNico : AppCompatActivity() {

    private lateinit var gotoAnimalHomeButton : Button
    private lateinit var registerButton : Button
    private lateinit var backToLoginButton : Button
    private lateinit var gotoUserRegistrationButton : Button
    private lateinit var gotoUserButton : Button
    private lateinit var gotoRegisterButton : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_shelter_account)

        initViewElements()

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

        registerButton.setOnClickListener {
            checkInput()
            try {
                // further code following when database is established
            } catch(e: java.lang.Exception) {
                displayGeneralErrorMessage()
            }
        }

        backToLoginButton.setOnClickListener {
            /*
             try {
                val intent = Intent(it.context, UserLoginActivity::class.java)
                startActivity(intent)
            } catch(e: java.lang.Exception) {
                 displayNetworkErrorMessage()
            }
             */
        }

        gotoAnimalHomeButton.setOnClickListener {
            // does nothing because we are already in its Activity
        }

        gotoUserRegistrationButton.setOnClickListener {
            try {
                val intent = Intent(it.context, RegisterUserAccountActivityNico::class.java)
                startActivity(intent)
            } catch (e: java.lang.Exception) {
                displayNetworkErrorMessage()
            }
        }
    }

    private fun initViewElements() {
        registerButton = findViewById<Button>(R.id.registerButton)
        gotoUserButton = findViewById<Button>(R.id.gotoUserRegistrationButton)
        gotoAnimalHomeButton = findViewById<Button>(R.id.gotoAnimalHomeButton)
        gotoRegisterButton = findViewById<Button>(R.id.registerButton)
        backToLoginButton = findViewById<Button>(R.id.backToLoginButton)
        gotoUserRegistrationButton = findViewById(R.id.gotoUserRegistrationButton)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val passwordConfirmInput = findViewById<EditText>(R.id.passwordConfirmInput)
    }

    /*
    private fun displayNetworkErrorMessage() {
        val errorTextView = findViewById<TextView>(R.id.errorTextView)
        errorTextView.setText("Etwas ist schiefgelaufen.  \n " +
                "Ueberpruefe deine Verbindung \n" +
                " oder versuche es später noch einmal")
        errorTextView.setVisibility(View.VISIBLE)
    }
     */

    /**
     * When trying to return to Activities where no sensitive data
     * is being send to, such as back to Login.
     */
    private fun displayNetworkErrorMessage() {
        Toast.makeText(this,"Überprüfe deine Netzwerkverbindung",
            Toast.LENGTH_LONG).show()
    }
    private fun displayGeneralErrorMessage() {
        Toast.makeText(this,"Irgendetwas ist falsch gelaufen",
            Toast.LENGTH_LONG).show()
    }

    private fun checkInput() {
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val emailInputString = emailInput.text.toString().trim()

        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val passwordInputString = passwordInput.text.toString().trim()

        val passwordConfirmInput = findViewById<EditText>(R.id.passwordConfirmInput)
        val passwordConfirmInputString = passwordConfirmInput.text.toString().trim()

        // checks if any input field is empty
        if (emailInputString.isEmpty()
            || passwordInputString.isEmpty()
            || passwordConfirmInputString.isEmpty()) {
            // throw java.lang.IllegalArgumentException("")
            emptyInputErrorMessage()
        }
        isValidEmail(emailInputString)
    }
    private fun emptyInputErrorMessage() {
        Toast.makeText(this, "Eingabefelder können nicht leer sein",
            Toast.LENGTH_LONG).show()
    }
    fun isValidEmail(emailInputString: String): Boolean {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailInputString).matches()) {
            return true
        } else
            Toast.makeText(this,"Ungültige E-Mail Adresse",
                Toast.LENGTH_LONG).show()
        return false
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
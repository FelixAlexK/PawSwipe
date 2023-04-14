package de.hhn.softwarelabor.pawswipeapp

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.*
import java.util.*


open class RegisterAccountActivity : AppCompatActivity() {

    protected lateinit var gotoShelterRegistrationButton : Button
    protected lateinit var registerButton : Button
    protected lateinit var backToLoginButton : Button
    protected lateinit var gotoUserRegistrationButton : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_shelter)

        initGUIElements()


        registerButton.setOnClickListener {
            registerAccount()
        }
        backToLoginButton.setOnClickListener {
            goBackToLoginActivity()
        }
        gotoShelterRegistrationButton.setOnClickListener {
            goBackToShelterRegistrationActivity()
        }
        gotoUserRegistrationButton.setOnClickListener {
            goBackToUserRegistrationActivity()
        }
    }

    fun registerAccount() {
        // if input isn't valid
        if (!checkInput()) {
            return
        }
        Toast.makeText(
            this, "Bis jetzt erfolgreich",
            Toast.LENGTH_LONG).show()
        // makeRegisterRequest()
    }

    /*
    fun makeRegisterRequest() {
        try {
            // @Nico this part needs to be refactored and cleared up as good as possible
            val client = OkHttpClient()
            val baseUrl = "http://45.146.253.199:8080"
            val path = "/profile/create"
            val url = baseUrl + path

            val emailInput = findViewById<EditText>(R.id.emailInputField)
            val passwordInput = findViewById<EditText>(R.id.passwordInputField)
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            val json = """{"email": "${email}", "password": "${password}"  }""".trimIndent()
            val body = json.toRequestBody("application/json".toMediaTypeOrNull())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()


            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val errorMessage = e.message
                    // Print the error message to the console
                    println("Request failed: -------------------------------- $errorMessage" )
                }

                override fun onResponse(call: Call, response: Response) {
                    println("SUCCESS ------------------------------")

                    val intent = Intent(this@RegisterShelterAccountActivityNico, CreateUserProfileActivity::class.java)
                    startActivity(intent)

                }
            })

            // further code following when database is established
        } catch(e: NetworkErrorException) {
            displayNetworkErrorMessage()
        } catch(e: java.lang.Exception) {
            displayGeneralErrorMessage()
        }
    }
    */

    protected fun goBackToLoginActivity() {
        try {
                                                // change this after testing
            val backToLoginIntent = Intent(this, MainActivity::class.java)
            startActivity(backToLoginIntent)
        } catch(e: NetworkErrorException) {
            displayNetworkErrorMessage()
        }
    }

    /**
     *  These will be overridden.
     */
    protected open fun goBackToUserRegistrationActivity() {
    }
    protected open fun goBackToShelterRegistrationActivity() {
    }


    protected fun initGUIElements() {
        try {
            gotoShelterRegistrationButton = findViewById<Button>(R.id.gotoShelterRegistrationButton)
            registerButton = findViewById<Button>(R.id.registerButton)
            backToLoginButton = findViewById<Button>(R.id.backToLoginButton)
            gotoUserRegistrationButton = findViewById(R.id.gotoUserRegistrationButton)

            val emailInputField = findViewById<EditText>(R.id.emailInputField)
            val passwordInputField = findViewById<EditText>(R.id.passwordInputField)
            val passwordConfirmInputField = findViewById<EditText>(R.id.passwordConfirmInputField)
        }
        catch(e: java.lang.Exception) {
            throw Exception("Could not initialize GUI elements")
        }
    }


    /**
     * Input sanitization and error handling
     * Checks if all inputs are valid or not
     */
    protected fun checkInput(): Boolean {
        // need to re-initialize the GUI elements
        try {
            val emailInputField = findViewById<EditText>(R.id.emailInputField)
            val emailInputString = emailInputField.text.toString().trim()

            val passwordInputField = findViewById<EditText>(R.id.passwordInputField)
            val passwordInputString = passwordInputField.text.toString().trim()

            val passwordConfirmInputField = findViewById<EditText>(R.id.passwordConfirmInputField)
            val passwordConfirmInputString = passwordConfirmInputField.text.toString().trim()

            return (isValidEmail(emailInputString)
                    && isValidPassword(passwordInputString, passwordConfirmInputString))
        }
        catch (e: java.lang.Exception) {
            displayGeneralErrorMessage()
            return false
        }
    }

    protected fun isValidEmail(emailInputString: String): Boolean {
        if (emailInputString.isEmpty()) {
            emptyInputErrorMessage()
            return false
        }

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailInputString).matches()) {
            return true
        }
        else {
            Toast.makeText(
                this, "Ungültige E-Mail Adresse",
                Toast.LENGTH_LONG).show()
            return false
        }
    }

    protected fun isValidPassword(passwordInputString: String,
                                passwordConfirmInputString: String): Boolean {
        // empty fields
        if (passwordConfirmInputString.isEmpty() || passwordInputString.isEmpty()) {
            emptyInputErrorMessage()
            return false
        }
        // different inputs
        if (!passwordConfirmInputString.equals(passwordInputString)) {
            differentPasswordsErrorMessage()
            return false
        }
        // password too short
        if (passwordConfirmInputString.length < 8 || passwordInputString.length < 8) {
            passwordLengthTooShortErrorMessage()
            return false
        }
        return true
    }

    /**
     * Different error messages
     */
    protected fun displayNetworkErrorMessage() {
        Toast.makeText(this,"Fehlgeschlagen. Überprüfe deine Netzwerkverbindung",
            Toast.LENGTH_LONG).show()
    }
    protected fun displayGeneralErrorMessage() {
        Toast.makeText(this,"Irgendetwas ist falsch gelaufen. Versuche es später nochmal.",
            Toast.LENGTH_LONG).show()
    }
    protected fun emptyInputErrorMessage() {
        Toast.makeText(this, "Eingabefelder können nicht leer sein",
            Toast.LENGTH_LONG).show()
    }
    protected fun differentPasswordsErrorMessage() {
        Toast.makeText(this, "Passwörter stimmen nicht überein",
            Toast.LENGTH_LONG).show()
    }
    protected fun passwordLengthTooShortErrorMessage() {
        Toast.makeText(this, "Passwort muss mindestens 8 Stellen lang sein",
            Toast.LENGTH_LONG).show()
    }

}

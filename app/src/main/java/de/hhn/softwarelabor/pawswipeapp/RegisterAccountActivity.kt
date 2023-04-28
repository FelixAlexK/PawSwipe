package de.hhn.softwarelabor.pawswipeapp

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.security.MessageDigest
import java.util.*


open class RegisterAccountActivity : AppCompatActivity() {

    private lateinit var gotoShelterRegistrationButton : Button
    private lateinit var registerButton : Button
    private lateinit var backToLoginButton : Button
    private lateinit var gotoUserRegistrationButton : Button
    // We are either a shelter or a user. Overwritten in the subclasses
    private var isShelter: Boolean = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_shelter)

        initGUIElements()


        registerButton.setOnClickListener {
            // registerAccount()
            testEncryption()
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

    fun testEncryption() {
        val emailInputString = findViewById<EditText>(R.id.emailInputField)
        val passwordInputString = findViewById<EditText>(R.id.passwordInputField)
        val email = emailInputString.text.toString()
        val password = passwordInputString.text.toString()

        // Email and password get hashed
        val emailHashed = hashStringToSHA256(email)
        val passwordHashed = hashStringToSHA256(password)

        Log.i(RegisterAccountActivity::javaClass.name, "Email hash: " + emailHashed)
        Log.i(RegisterAccountActivity::javaClass.name, "Password Hashed: " + passwordHashed)
    }

    fun registerAccount() {
        // cancels registration when inputs are not valid
        if (!checkInput()) {
            return
        }
        makeRegisterRequest()
    }

    private fun makeRegisterRequest() {
        try {
            // @Nico this part needs to be refactored and cleared up as good as possible
            val client = OkHttpClient()
            val baseUrl = "http://45.146.253.199:8080"
            val path = "/account/create"
            val url = baseUrl + path

            val emailInputString = findViewById<EditText>(R.id.emailInputField)
            val passwordInputString = findViewById<EditText>(R.id.passwordInputField)
            val email = emailInputString.text.toString()
            val password = passwordInputString.text.toString()

            // Email and password get hashed
            val emailHashed = hashStringToSHA256(email)
            val passwordHashed = hashStringToSHA256(password)

            val json = """{"email": "${emailHashed}", "password": "${passwordHashed}"  }""".trimIndent()
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


                }
            })

            // everything is successful
            proceedToProfileCreation()

            // further code following when database is established
        } catch(e: NetworkErrorException) {
            displayNetworkErrorMessage()
        } catch(e: java.lang.Exception) {
            displayGeneralErrorMessage()
        }
    }

    /**
     * Redirects to other activities
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

    protected fun proceedToProfileCreation() {
        if (isShelter) {
            val intent = Intent(this, CreateShelterActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, CreateUserProfileActivity::class.java)
            startActivity(intent)
        }
    }

    protected open fun goBackToUserRegistrationActivity() {
    }
    protected open fun goBackToShelterRegistrationActivity() {
    }
    /** -------------------------------------------------------------------------------------- */

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
    /** -------------------------------------------------------------------------------------- */

    /**
     * Hashes data (for instance a password) to SHA-256
     */
    fun hashStringToSHA256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(input.toByteArray())
        return hashBytes.joinToString("") { byte ->
            "%02x".format(byte)
        }
    }

}

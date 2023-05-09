package de.hhn.softwarelabor.pawswipeapp

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.security.MessageDigest
import java.util.*


open class RegisterAccountActivity : AppCompatActivity() {
    private var email = ""
    private var passwordHashed = ""


    private lateinit var registerAsUserButton : Button
    private lateinit var registerAsShelterButton : Button
    private lateinit var backToLoginButton : Button
    private lateinit var passwordInputEditText : EditText
    private lateinit var mailInputEditText : EditText
    private lateinit var passwordConfirmInputEditText :EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)

        initGUIElements()

        registerAsUserButton.setOnClickListener {
            proceedToUserProfileCreation()
        }
        registerAsShelterButton.setOnClickListener {
            proceedToShelterProfileCreation()
        }
        backToLoginButton.setOnClickListener {
            goBackToLoginActivity()
        }
    }



    /** -------------------------Initialization of GUI elements ------------------------------- */

    private fun initGUIElements() {
        try {
            registerAsUserButton = findViewById<Button>(R.id.registerAsUserButton)
            registerAsShelterButton = findViewById<Button>(R.id.registerAsShelterButton)
            backToLoginButton = findViewById<Button>(R.id.backToLoginButton)

            mailInputEditText = findViewById<EditText>(R.id.emailInputField)
            passwordInputEditText = findViewById<EditText>(R.id.passwordInputField)
            passwordConfirmInputEditText = findViewById<EditText>(R.id.passwordConfirmInputField)
        }
        catch(e: java.lang.Exception) {
            throw Exception("Could not initialize GUI elements")
        }
    }
    /** ----------------------------------- redirects ----------------------------------- */

    private fun goBackToLoginActivity() {
        try {
            val backToLoginIntent = Intent(this, LoginActivity::class.java)
            startActivity(backToLoginIntent)
        } catch(e: NetworkErrorException) {
            displayNetworkErrorMessage()
        }
    }

    /**
     * Redirects to profiles after registration has been successful
     */
    private fun proceedToUserProfileCreation() {
        try{
            if (!checkInput()) {
                return
            }
            
            email = mailInputEditText.text.toString()
            passwordHashed = stringToSHA256(passwordInputEditText.text.toString())
            
    
            val intent = Intent(this, CreateUserProfileActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("passwordHashed", passwordHashed)
            startActivity(intent)
        } catch(ex: Exception){
            println(ex.message)
        }
        
    }
    
    private fun proceedToShelterProfileCreation() {
    
        try{
            if (!checkInput()) {
                return
            }
        
            email = mailInputEditText.text.toString()
            passwordHashed = stringToSHA256(passwordInputEditText.text.toString())
        
        
            val intent = Intent(this, CreateShelterActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("passwordHashed", passwordHashed)
            // intent.putExtra("accountType", accountType)
            startActivity(intent)
        } catch(ex: Exception){
            println(ex.message)
        }
    }

    /** --------------------------------------------------------------------------------------- */

    /**
     * Input sanitization and error handling
     * Checks if all inputs are valid or not
     */
    private fun checkInput(): Boolean {
        // need to re-initialize the GUI elements
        try {

            return (isValidEmail(mailInputEditText.text.toString())
                    && isValidPassword(passwordConfirmInputEditText.text.toString(), passwordInputEditText
                .text
                .toString()))
        }
        catch (e: java.lang.Exception) {
            displayGeneralErrorMessage()
            return false
        }
    }

    private fun isValidEmail(emailInputString: String): Boolean {
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

    private fun isValidPassword(passwordInputString: String,
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
    private fun displayNetworkErrorMessage() {
        Toast.makeText(this,"Fehlgeschlagen. Überprüfe deine Netzwerkverbindung",
            Toast.LENGTH_LONG).show()
    }
    private fun displayGeneralErrorMessage() {
        Toast.makeText(this,"Irgendetwas ist falsch gelaufen. Versuche es später nochmal.",
            Toast.LENGTH_LONG).show()
    }
    private fun emptyInputErrorMessage() {
        Toast.makeText(this, "Eingabefelder können nicht leer sein",
            Toast.LENGTH_LONG).show()
    }
    private fun differentPasswordsErrorMessage() {
        Toast.makeText(this, "Passwörter stimmen nicht überein",
            Toast.LENGTH_LONG).show()
    }
    private fun passwordLengthTooShortErrorMessage() {
        Toast.makeText(this, "Passwort muss mindestens 8 Stellen lang sein",
            Toast.LENGTH_LONG).show()
    }
    /** -------------------------------------------------------------------------------------- */

    /**
     * Hashes data (for instance a password) to SHA-256
     */
    private fun stringToSHA256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(input.toByteArray())
        return hashBytes.joinToString("") { byte ->
            "%02x".format(byte)
        }
    }

}

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
    // , AdapterView.OnItemSelectedListener
    private lateinit var email : String
    private lateinit var passwordHashed : String

    private lateinit var registerButton : Button
    private lateinit var backToLoginButton : Button
    private lateinit var accountTypeSpinner: Spinner
    private lateinit var accountType: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)

        initGUIElements()
        initArrayAdapter()

        registerButton.setOnClickListener {
            registerAccount()
        }
        backToLoginButton.setOnClickListener {
            goBackToLoginActivity()
        }
    }


    fun registerAccount() {
        // cancels registration when inputs are not valid
        if (!checkInput()) {
            return
        }
        if (accountType.equals("")) {
            Toast.makeText(
                this, "Keine Accountart ausgewählt",
                Toast.LENGTH_LONG).show()
            return
        }
        proceedToProfileCreation()
    }


    /** -------------------------Initialization of GUI elements ------------------------------- */

    protected fun initGUIElements() {
        try {
            registerButton = findViewById<Button>(R.id.registerButton)
            backToLoginButton = findViewById<Button>(R.id.backToLoginButton)

            val emailInputField = findViewById<EditText>(R.id.emailInputField)
            val passwordInputField = findViewById<EditText>(R.id.passwordInputField)
            val passwordConfirmInputField = findViewById<EditText>(R.id.passwordConfirmInputField)
        }
        catch(e: java.lang.Exception) {
            throw Exception("Could not initialize GUI elements")
        }
    }

    protected fun initArrayAdapter() {
        accountType = ""

        val accountTypeSpinner: Spinner = findViewById(R.id.accountTypeSpinner)
        // accountTypeSpinner.onItemSelectedListener = this
        // val accountType = spinner.selectedItem.toString()

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.accountTypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            accountTypeSpinner.adapter = adapter
        }


    }
    /*
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        // Exclude the hint item from being treated as a valid selection
        // An item was selected. You can retrieve the selected item using
        // accountType = parent.getItemAtPosition(p2).toString()
        // val accountType: String = accountTypeSpinner.getSelectedItem().toString()
        // val accountType: String = accountTypeSpinner.getItemAtPosition(p2) as String

        // "lateinit property accountTypeSpinner has not been initialized"
        // accountType = accountTypeSpinner.getItemAtPosition(p2) as String
        accountType = accountTypeSpinner.selectedItem.toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

        Toast.makeText(
            this, "Nichts ausgewählt",
            Toast.LENGTH_LONG).show()
        return
    }
    */
    /** ----------------------------------- redirects ----------------------------------- */

    protected fun goBackToLoginActivity() {
        try {
            val backToLoginIntent = Intent(this, LoginActivity::class.java)
            startActivity(backToLoginIntent)
        } catch(e: NetworkErrorException) {
            displayNetworkErrorMessage()
        }
    }
    /**
     * Redirects to profile after registration has been successful
     */
    protected fun proceedToProfileCreation() {


        val passwordInputString = findViewById<EditText>(R.id.passwordInputField)
        val password = passwordInputString.text.toString()

        // Email and password get hashed
        val passwordHashed = hashStringToSHA256(password)

        if (accountType.equals("Tierheim")) {
            val intent = Intent(this, CreateShelterActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("passwordHashed", passwordHashed)
            // intent.putExtra("accountType", accountType)
            startActivity(intent)
        }
        else {
            val intent = Intent(this, CreateUserProfileActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("passwordHashed", passwordHashed)
            // intent.putExtra("accountType", accountType)
            startActivity(intent)
        }
    }

    /** --------------------------------------------------------------------------------------- */

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
    private fun hashStringToSHA256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(input.toByteArray())
        return hashBytes.joinToString("") { byte ->
            "%02x".format(byte)
        }
    }

}

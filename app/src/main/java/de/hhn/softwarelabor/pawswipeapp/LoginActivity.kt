package de.hhn.softwarelabor.pawswipeapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
import java.security.MessageDigest

private const val DISCRIMINATOR_SHELTER = "shelter"
private const val DISCRIMINATOR_PROFILE = "profile"

/**
 * Login activity
 *
 * @constructor Create empty Login activity
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var loginUserButton: Button
    private lateinit var loginShelterButton: Button
    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    private lateinit var loginRegisterButton: Button
    private lateinit var loginLoginButton: Button

    private var profile: ProfileApi = ProfileApi()
    private var isShelter = false
    private var isUser = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()

        loginUserButton.setOnClickListener {
            try {
                loginUserButton.setBackgroundColor(Color.GREEN)
                loginShelterButton.setBackgroundColor(
                    resources.getColor(
                        R.color.pawswipe_orange_700,
                        null
                    )
                )
            } catch (e: Resources.NotFoundException) {
                Log.e(TAG, e.message.toString())
                e.printStackTrace()
            }

            isUser = true
            isShelter = false
        }

        loginShelterButton.setOnClickListener {
            try {
                loginShelterButton.setBackgroundColor(Color.GREEN)
                loginUserButton.setBackgroundColor(
                    resources.getColor(
                        R.color.pawswipe_orange_700,
                        null
                    )
                )
            } catch (e: Resources.NotFoundException) {
                Log.e(TAG, e.message.toString())
                e.printStackTrace()
            }

            isShelter = true
            isUser = false
        }

        loginLoginButton.setOnClickListener {
            val email: String = loginEmailEditText.text.toString()
            val password: String = stringToSHA256(loginPasswordEditText.text.toString())
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (isShelter) {
                    loginShelter(email, password)
                } else if (isUser) {
                    loginUser(email, password)
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this, getString(R.string.login_shelter_or_user),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                runOnUiThread {
                    Toast.makeText(
                        this,
                        getString(R.string.login_valid_email_and_password),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        loginRegisterButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterAccountActivity::class.java)
            startActivity(intent)
        }


    }

    /**
     * Login user
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     */
    private fun loginUser(email: String, password: String) {
        try {
            profile.getUserProfileByEmail(email) { user, error ->
                if (error != null) {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            getString(R.string.login_no_account_found_with_email),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.e(TAG, error.message.toString())
                } else if (user != null && user.discriminator.equals(
                        DISCRIMINATOR_PROFILE,
                        ignoreCase = true
                    )
                ) {
                    if (password == user.password && email == user.email) {
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                getString(R.string.login_success, user.username),
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@LoginActivity, MatchActivityNico::class.java)
                            intent.putExtra("id", user.profile_id)
                            startActivity(intent)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                getString(R.string.login_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            getString(R.string.login_user_error),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            e.printStackTrace()
        }

    }

    /**
     * Login shelter
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     */
    private fun loginShelter(email: String, password: String) {

        try {
            profile.getUserProfileByEmail(email) { shelter, error ->
                if (error != null) {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            getString(R.string.login_no_account_found_with_email),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.e(TAG, error.message.toString())
                } else if (shelter != null && shelter.discriminator.equals(
                        DISCRIMINATOR_SHELTER,
                        ignoreCase = true
                    )
                ) {
                    if (password == shelter.password && email == shelter.email) {
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                getString(R.string.login_success, shelter.username),
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@LoginActivity, MatchActivityNico::class.java)
                            startActivity(intent)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                getString(R.string.login_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            getString(R.string.login_shelter_error),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            e.printStackTrace()
        }


    }

    private fun stringToSHA256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(input.toByteArray())
        return hashBytes.joinToString("") { byte ->
            "%02x".format(byte)
        }
    }

    /**
     * Initialize UI elements
     *
     */
    private fun init() {
        try {
            loginUserButton = findViewById(R.id.loginUser_button)
            loginShelterButton = findViewById(R.id.loginShelter_button)
            loginEmailEditText = findViewById(R.id.loginEmail_editText)
            loginPasswordEditText = findViewById(R.id.loginPassword_editText)
            loginRegisterButton = findViewById(R.id.loginRegister_button)
            loginLoginButton = findViewById(R.id.loginLogin_button)
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message.toString())
            e.printStackTrace()
        }
    }
}
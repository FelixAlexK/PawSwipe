package de.hhn.softwarelabor.pawswipeapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
import de.hhn.softwarelabor.pawswipeapp.utils.AppData
import java.security.MessageDigest

private const val DISCRIMINATOR_SHELTER = "shelter"
private const val DISCRIMINATOR_PROFILE = "profile"

/**
 * Login activity
 *
 * @constructor Create empty Login activity
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    private lateinit var loginRegisterButton: Button
    private lateinit var loginLoginButton: Button

    private var profile: ProfileApi = ProfileApi()

    private var backPressedOnce = false
    private val timerDuration = 3000 // 3 Sekunden
    
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        
        if (backPressedOnce) {
            finishAffinity()    // Beendet alle Activities und die App
            return
        }
        
        backPressedOnce = true
        Toast.makeText(
            this,
            getString(R.string.zum_beenden_der_app),
            Toast.LENGTH_SHORT
        ).show()
        
        Handler(Looper.getMainLooper()).postDelayed({
            backPressedOnce = false
        }, timerDuration.toLong())
        
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()

        loginLoginButton.setOnClickListener {
            val email: String = loginEmailEditText.text.toString()
            val password: String = stringToSHA256(loginPasswordEditText.text.toString())

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginAccount(email, password)
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

    private fun loginAccount(email: String, password: String) {
        try {
            profile.getUserProfileByEmail(email) { profile, error ->
                if (error != null) {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            getString(R.string.login_no_account_found_with_email),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.e(TAG, error.message.toString())
                }
                else if (profile != null) {
                    if (password == profile.password && email == profile.email) {
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                getString(R.string.login_success, profile.username),
                                Toast.LENGTH_SHORT
                            ).show()
                            AppData.setID(this, profile.profile_id ?: 0)
                            AppData.setMail(this, profile.email)
                            AppData.setPassword(this, stringToSHA256(loginPasswordEditText.text.toString()))
                            AppData.setDiscriminator(this, profile.discriminator)

                            val intent = when (profile.discriminator) {
                                DISCRIMINATOR_PROFILE -> Intent(
                                    this@LoginActivity,
                                    MatchActivity::class.java
                                )

                                DISCRIMINATOR_SHELTER -> Intent(
                                    this@LoginActivity,
                                    AnimalListActivity::class.java
                                )

                                else -> null
                            }

                            if (intent != null) {
                                intent.putExtra("id", profile.profile_id)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    getString(R.string.login_invalid_discriminator),
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
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
                            getString(R.string.login_null_error),
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
            loginEmailEditText = findViewById(R.id.loginEmail_editText)
            loginPasswordEditText = findViewById(R.id.loginPassword_editText)
            loginRegisterButton = findViewById(R.id.loginRegister_button)
            loginLoginButton = findViewById(R.id.loginLogin_button)
            loginEmailEditText.setText(AppData.getMail(this@LoginActivity))
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message.toString())
            e.printStackTrace()
        }
    }
}
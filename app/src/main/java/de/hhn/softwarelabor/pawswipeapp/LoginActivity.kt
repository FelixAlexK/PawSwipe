package de.hhn.softwarelabor.pawswipeapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var loginUserButton: Button
    private lateinit var loginShelterButton: Button
    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    private lateinit var loginRegisterButton: Button
    private lateinit var loginLoginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
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
            e.printStackTrace()
        }
    }
}
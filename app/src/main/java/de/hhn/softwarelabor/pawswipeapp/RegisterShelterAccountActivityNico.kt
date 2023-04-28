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
import java.util.*


class RegisterShelterAccountActivityNico : RegisterAccountActivity() {

    // we are indeed a shelter
    private var isShelter: Boolean = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_shelter)

        Log.i(RegisterShelterAccountActivityNico::javaClass.name, "SHELTER Register")
        // overrides the unchanged button color
        // gotoUserRegistrationButton.setBackgroundColor(R.color.)
    }

    override fun goBackToUserRegistrationActivity() {
        try {
            // val intent = Intent(it.context, SomeActivity::class.java)
            val intent = Intent(this, RegisterUserAccountActivityNico::class.java)
            startActivity(intent)
        } catch (e: NetworkErrorException) {
            displayNetworkErrorMessage()
        }
    }

}
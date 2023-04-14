package de.hhn.softwarelabor.pawswipeapp

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import okhttp3.*
import java.util.*


class RegisterUserAccountActivityNico : RegisterAccountActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        Log.i(RegisterUserAccountActivityNico::javaClass.name, "USER Register")
        // overrides the unchanged button color
        // gotoUserRegistrationButton.setBackgroundColor(R.color.purple);

    }

    override fun goBackToShelterRegistrationActivity() {
        try {
            val intent = Intent(this, RegisterShelterAccountActivityNico::class.java)
            startActivity(intent)
        } catch (e: NetworkErrorException) {
            displayNetworkErrorMessage()
        }
    }

}
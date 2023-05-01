package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import de.hhn.softwarelabor.pawswipeapp.api.UserProfileApi
import java.util.*

class CreateShelterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shelter)

        val shelterName : EditText = findViewById(R.id.shelterEditText)
        val homepage : EditText = findViewById(R.id.homepageEditText)
        val plz : EditText = findViewById(R.id.plzEditText)
        val shelterAddress : EditText = findViewById(R.id.shelterAddressEditText)
        val phoneNumber : EditText = findViewById(R.id.phoneNumberEditText)
        val openingHours: EditText = findViewById(R.id.openingHoursEditText)
        val streetAndNumber : EditText = findViewById(R.id.streetPlusNrEditText)

        val cancel : Button = findViewById(R.id.clearButton)
        val create : Button = findViewById(R.id.doneButton)

        create.setOnClickListener {


            val creationDate : Date = Calendar.getInstance().time
            var street : String? = null
            var streetNr : Int? = null
            val streetAndNumberString : String? = streetAndNumber.text.toString().takeIf { it.isNotBlank() }
            if (streetAndNumberString != null) {
                if (!streetAndNumber.text.toString().contains("\\s".toRegex())) {
                    Toast.makeText(
                        this@CreateShelterActivity,
                        getString(R.string.seperateStreetAndNumber),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                else{
                    street  = streetAndNumberString.split(" ")[0]
                    streetNr  = streetAndNumberString.split(" ")[1].toInt()
                }

            }




            val shelterNameString : String? = shelterName.text.toString().takeIf { it.isNotBlank() }
            val phoneNumberString : String? = phoneNumber.text.toString().takeIf { it.isNotBlank() }
            val openingHrsString : String?= openingHours.text.toString().takeIf { it.isNotBlank() }
            val homepageString : String? = homepage.text.toString().takeIf { it.isNotBlank() }
            val shelterAddressString : String?= shelterAddress.text.toString().takeIf { it.isNotBlank() }
            val postalCodeString : String?= plz.text.toString().takeIf{it.isNotBlank()}

            val userProfileApi = UserProfileApi()

            userProfileApi.createUserProfile(shelterNameString, null,
                null, "password123", creationDate, "email@mail.de",
                null,null, phoneNumberString,
                openingHrsString, street, "de", shelterAddressString,
                streetNr, homepageString, postalCodeString, "shelter")
            { profile, error ->


                if(error != null){

                }
                else if(profile != null){

                }
            }

            runOnUiThread {
                   Toast.makeText(this@CreateShelterActivity, "Profil erfolgreich angelegt", Toast.LENGTH_SHORT).show()
            }


        }

        cancel.setOnClickListener {
            val intent = Intent(this, RegisterShelterAccountActivityNico::class.java)
            startActivity(intent)
        }
    }
}
package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
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
        val street : EditText = findViewById(R.id.shelterStreetEditText)
        val streetNumber : EditText = findViewById(R.id.streetNumberEditText)

        val cancel : Button = findViewById(R.id.clearButton)
        val create : Button = findViewById(R.id.doneButton)

        val email: String = intent.getStringExtra("email").toString()
        val password : String = intent.getStringExtra("hashedPassword").toString()


        create.setOnClickListener {


            //val creationDate : Date = Calendar.getInstance().time

            val shelterStreetString : String?= street.text.toString().takeIf { it.isNotBlank() }
            val shelterStreetNrString : String? = streetNumber.text.toString().takeIf { it.isNotBlank() }
            val shelterNameString : String? = shelterName.text.toString().takeIf { it.isNotBlank() }
            val phoneNumberString : String? = phoneNumber.text.toString().takeIf { it.isNotBlank() }
            val openingHrsString : String?= openingHours.text.toString().takeIf { it.isNotBlank() }
            val homepageString : String? = homepage.text.toString().takeIf { it.isNotBlank() }
            val shelterAddressString : String?= shelterAddress.text.toString().takeIf { it.isNotBlank() }
            val postalCodeString : String?= plz.text.toString().takeIf{it.isNotBlank()}

            val userProfileApi = ProfileApi()

            userProfileApi.createUserProfile(null,shelterNameString, email,
                phoneNumberString, null, null, password,
                null,null, openingHrsString,
                shelterStreetString, "de", shelterAddressString, shelterStreetNrString,
                homepageString, postalCodeString, "shelterFN", "shelterLN","shelter")
            { profile, error ->


                if(error != null){

                }
                else if(profile != null){

                }
            }

            runOnUiThread {
                   Toast.makeText(this@CreateShelterActivity, getString(R.string.profileCreated), Toast.LENGTH_SHORT).show()
            }


        }

        cancel.setOnClickListener {
            val intent = Intent(this, RegisterAccountActivity::class.java)
            startActivity(intent)
        }
    }
}
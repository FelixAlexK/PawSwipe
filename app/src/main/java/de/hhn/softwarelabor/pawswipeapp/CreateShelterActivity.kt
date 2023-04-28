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
            if(shelterName.text.isEmpty()|| homepage.text.isEmpty()||plz.text.isEmpty()||
                shelterAddress.text.isEmpty()|| phoneNumber.text.isEmpty()){
                Toast.makeText(this@CreateShelterActivity, "Bitte alle Textfelder ausfülllen" , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            else{
                val creationDate : Date = Calendar.getInstance().time
                val street : String = streetAndNumber.text.toString().split(" ")[0]
                val streetNr : Int = streetAndNumber.text.toString().split(" ")[1].toInt()

                val userProfileApi = UserProfileApi()

                userProfileApi.createUserProfile(shelterName.text.toString(), null,
                    null, "null", creationDate, "email@mail.de",
                    null,null, phoneNumber.text.toString(),
                    openingHours.text.toString(), street, "de", shelterAddress.text.toString(),
                    streetNr, homepage.text.toString(), plz.text.toString().toInt(), "shelter")
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

        }

        cancel.setOnClickListener {
            val intent = Intent(this, RegisterShelterAccountActivityNico::class.java)
            startActivity(intent)
        }
    }
}
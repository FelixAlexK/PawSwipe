package de.hhn.softwarelabor.pawswipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateShelterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shelter)

        val shelterName : EditText = findViewById(R.id.shelterEditText)
        val owner : EditText = findViewById(R.id.openingHoursEditText)
        val homepage : EditText = findViewById(R.id.homepageEditText)
        val plz : EditText = findViewById(R.id.plzEditText)
        val shelterAddress : EditText = findViewById(R.id.shelterAddressEditText)
        val phoneNumber : EditText = findViewById(R.id.phoneNumberEditText)

        val cancel : Button = findViewById(R.id.clearButton)
        val create : Button = findViewById(R.id.doneButton)

        create.setOnClickListener {
            if(shelterName.text.isEmpty()|| owner.text.isEmpty()|| homepage.text.isEmpty()||plz.text.isEmpty()||
                shelterAddress.text.isEmpty()|| phoneNumber.text.isEmpty()){
                Toast.makeText(this@CreateShelterActivity, "Bitte alle Textfelder ausfülllen" , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(plz.length() != 5){
                Toast.makeText(this@CreateShelterActivity, "Die PLZ muss genau 5 Zeichen lang sein" , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            else{
                Toast.makeText(this@CreateShelterActivity, "Profil erfolgreich angelegt", Toast.LENGTH_SHORT).show()
            }

        }

        cancel.setOnClickListener {

        }
    }
}
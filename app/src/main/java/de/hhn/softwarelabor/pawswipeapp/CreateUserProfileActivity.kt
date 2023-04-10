package de.hhn.softwarelabor.pawswipeapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateUserProfileActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user_profile)

        //Data Fields
        val prename : EditText = findViewById(R.id.prenameEditText)
        val name : EditText = findViewById(R.id.nameEditText)
        val age : EditText = findViewById(R.id.agesEditText)
        val address : EditText = findViewById(R.id.addressEditText)
        val done : Button = findViewById(R.id.doneUserButton)
        val cancel : Button = findViewById(R.id.clearUserButton)


        done.setOnClickListener{
            if (prename.length()==0 || name.length()==0 || age.length()==0||address.length()==0){
                Toast.makeText(this@CreateUserProfileActivity, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(age.text.toString().toInt() < 18 || age.text.toString().toInt() >110){

                Toast.makeText(this@CreateUserProfileActivity, "Bitte ein Alter zwischen 18 und 110 Jahren auswählen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            else{
                Toast.makeText(this@CreateUserProfileActivity, "Profil erfolgreich angelegt", Toast.LENGTH_SHORT).show()
            }


        }

        cancel.setOnClickListener {

        }
    }




}
package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import de.hhn.softwarelabor.pawswipeapp.api.UserProfileApi


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
        //val picture : View = findViewById(R.id.pictureView)
        lateinit var picture : Array<Byte>


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

                val username : String = prename.text.toString() + " " + name.text.toString()
                val birthdate : String
                val userProfileApi = UserProfileApi()

                userProfileApi.createUserProfile(username, null,
                    "description", "password", null, "email@mail.de",
                    null, null, null,
                    null, null, "de", "city",
                    null, null, 74072, "profile")
                { profile, error ->


                    if(error != null){

                    }
                    else if(profile != null){

                    }
                }



                Toast.makeText(this@CreateUserProfileActivity, getString(R.string.profileCreated), Toast.LENGTH_SHORT).show()
            }
        }
        cancel.setOnClickListener {
            val intent = Intent(this, RegisterUserAccountActivityNico::class.java)
            startActivity(intent)
        }
    }
}
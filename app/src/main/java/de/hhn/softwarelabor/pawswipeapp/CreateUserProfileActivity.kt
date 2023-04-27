package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.UserProfileApi
import java.text.SimpleDateFormat
import java.util.*


class CreateUserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user_profile)

        //Data Fields
        val prename : EditText = findViewById(R.id.prenameEditText)
        val name : EditText = findViewById(R.id.nameEditText)
        val birthdate : EditText = findViewById(R.id.agesEditText)
        val address : EditText = findViewById(R.id.addressEditText)
        val done : Button = findViewById(R.id.doneUserButton)
        val cancel : Button = findViewById(R.id.clearUserButton)
        val upload : Button = findViewById(R.id.uploadPictureButton)
        val description : EditText= findViewById(R.id.descriptionEditText)
        val username : EditText = findViewById(R.id.usernameEditText)
        val plz : EditText = findViewById(R.id.postalAddressEditText)
        val streetAndNumber : EditText = findViewById(R.id.streetEditText)
        var password : String = intent.getStringExtra("password").toString()
        var email : String = intent.getStringExtra("email").toString()
        //val picture : View = findViewById(R.id.pictureView)
        //lateinit var picture : Array<Byte>



        done.setOnClickListener{
            if (prename.length()==0 || name.length()==0 ||address.length()==0){
                Toast.makeText(this@CreateUserProfileActivity, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            else{
                val creationDate : Date = Calendar.getInstance().time
                val street : String = streetAndNumber.text.toString().split(" ")[0]
                val streetNr : Int = streetAndNumber.text.toString().split(" ")[1].toInt()
                val birthday : Date? = SimpleDateFormat("dd-MM-yyyy").parse(birthdate.text.toString())


                val userProfileApi = UserProfileApi()

                userProfileApi.createUserProfile(username.text.toString(), null,
                    description.text.toString(), "password", creationDate, "email@mail.de",
                    null, birthday, null,
                    null, street, "de", address.text.toString(),
                    streetNr, null, plz.text.toString().toInt(), "profile")
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


        upload.setOnClickListener {

        }


    }
}
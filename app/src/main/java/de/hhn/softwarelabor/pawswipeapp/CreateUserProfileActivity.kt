package de.hhn.softwarelabor.pawswipeapp


import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.UserProfileApi
import java.text.SimpleDateFormat
import java.util.*


class CreateUserProfileActivity : AppCompatActivity() {
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    // This function will be called when the user clicks a button to select an image from the gallery
    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // This function will be called after the user selects an image from the gallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data

            // Display the selected image in an ImageView
            val imageView = findViewById<ImageView>(R.id.pictureView)
            imageView.setImageURI(imageUri)
        }
        }

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

        //picture.setImageResource(R.drawable.wf)
        //lateinit var picture : Array<Byte>
        done.setOnClickListener{
            /**
             *

            if (prename.length()==0 || name.length()==0 ||address.length()==0){
                Toast.makeText(this@CreateUserProfileActivity, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }*/

           // else{
                val creationDate : Date = Calendar.getInstance().time
                val street : String = streetAndNumber.text.toString().split(" ")[0]
                val streetNr : Int = streetAndNumber.text.toString().split(" ")[1].toInt()
              //  val birthday : Date? = SimpleDateFormat("dd/MM/yyyy").parse(birthdate.text.toString())


                val userProfileApi = UserProfileApi()

                userProfileApi.createUserProfile(username.text.toString(), null,
                    description.text.toString(), "password", creationDate, "email@mail.de",
                    null, null, null,
                    null, street, "de", address.text.toString(),
                    streetNr, null, plz.text.toString().toInt(), "profile")
                { profile, error ->


                    if(error != null){

                    }
                    else if(profile != null){

                    }
               // }
              runOnUiThread {
                  Toast.makeText(this@CreateUserProfileActivity, getString(R.string.profileCreated), Toast.LENGTH_SHORT).show()
              }
            }
        }
        cancel.setOnClickListener {
            val intent = Intent(this, RegisterAccountActivity::class.java)
            startActivity(intent)
        }

        upload.setOnClickListener {
                selectImageFromGallery()
            }
    }

}
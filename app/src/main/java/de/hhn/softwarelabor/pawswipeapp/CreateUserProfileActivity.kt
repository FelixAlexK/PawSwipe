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
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream


class CreateUserProfileActivity : AppCompatActivity() {
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
    private lateinit var imageView :ImageView

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
        val street : EditText = findViewById(R.id.streetEditText)
        val streetNr : EditText = findViewById(R.id.streetNrEditText)
        var password : String = intent.getStringExtra("password").toString()
        var email : String = intent.getStringExtra("email").toString()

        lateinit var imageArray : Array<Byte>

                imageView = findViewById(R.id.pictureView)

        done.setOnClickListener{

            val creationDate : Date = Calendar.getInstance().time



            lateinit var birthday : String
            if(birthdate.text.toString().isNotEmpty()){
                val inputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val outPutDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

                val date = inputDateFormat.parse(birthdate.text.toString())
                birthday  = outPutDateFormat.format(date)
            }
            else{
                birthday = null.toString()
            }


            if(imageView.drawable != null){
                val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap

                // Convert Bitmap to byte array
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                // Convert the ByteArray to Array<Byte>
                imageArray = byteArray.toTypedArray()
            }
            val streetString : String? = street.text.toString().takeIf { it.isNotBlank() }

            val streetNrString : String? = streetNr.text.toString().takeIf { it.isNotBlank() }

            val postalCode: String? = plz.text.toString().takeIf { it.isNotBlank() }

            val usernameString : String? = username.text.toString().takeIf { it.isNotBlank() }

            val cityString : String? = address.text.toString().takeIf { it.isNotBlank() }

            val descriptionString : String? = description.text.toString().takeIf { it.isNotBlank() }

            // Creating instance of the UserProfileAPI
            val userProfileApi = UserProfileApi()

            userProfileApi.createUserProfile(usernameString, imageArray,
            descriptionString, "password", creationDate, "email@mail.de",
            null, birthday, null,
            null, streetString, "de", cityString,
            streetNrString, null, postalCode, "profile")
            { profile, error ->

            if(error != null){


            }
            else if(profile != null){

            }
            }

            /**
             *  TestRequest

            userProfileApi.createUserProfile("Testrequest", null,
                null, "password", creationDate, "email@mail.de",
                null, null, null,
                null, null, "de", null,
                null, null, null, "profile")
            { profile, error ->

                if(error != null){


                }
                else if(profile != null){

                }
            }
            */
            //Toast message if Registration was successful
            runOnUiThread {
                Toast.makeText(this@CreateUserProfileActivity, getString(R.string.profileCreated), Toast.LENGTH_SHORT).show()
            }
        }
        //Click Listener for the Cancel Button, returns to Registration Activity
        cancel.setOnClickListener {
            val intent = Intent(this, RegisterUserAccountActivityNico::class.java)
            startActivity(intent)
        }
        //Click Listener for uploadPicture Button
        upload.setOnClickListener {
                selectImageFromGallery()
            }
    }
}
package de.hhn.softwarelabor.pawswipeapp


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
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

    private var newFragment: DatePickerFragment = DatePickerFragment()

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
    private var datePickerFragment: DatePickerFragment = DatePickerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user_profile)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        //Data Fields
        val prename : EditText = findViewById(R.id.prenameEditText)
        val name : EditText = findViewById(R.id.nameEditText)
        val birthdate : Button = findViewById(R.id.userBirthdayButton)
        val address : EditText = findViewById(R.id.addressEditText)
        val done : Button = findViewById(R.id.doneUserButton)
        val cancel : Button = findViewById(R.id.clearUserButton)
        val upload : Button = findViewById(R.id.uploadPictureButton)
        val description : EditText= findViewById(R.id.descriptionEditText)
        val username : EditText = findViewById(R.id.usernameEditText)
        val plz : EditText = findViewById(R.id.postalAddressEditText)
        val street : EditText = findViewById(R.id.streetEditText)
        val streetNr : EditText = findViewById(R.id.houseNumberEditText)


        val password : String = intent.getStringExtra("passwordHashed").toString()
        val email : String = intent.getStringExtra("email").toString()

        imageView = findViewById(R.id.pictureView)

        done.setOnClickListener{



            datePickerFragment.setOnDatePickedListener { date ->
                birthdate.text = date
            }
            birthdate.apply {

                setOnClickListener {
                    showDatePickerDialog(this)
                }
            }

            var imageArray : Array<Byte>? = null

            if(imageView.drawable != null){
                val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap

                // Convert Bitmap to byte array
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                // Convert the ByteArray to Array<Byte>
                imageArray = byteArray.toTypedArray()
            }
            val creationDate : Date? = getCreationDate()


            val streetString : String? = street.text.toString().takeIf { it.isNotBlank() }

            val streetNrString : String? = streetNr.text.toString().takeIf { it.isNotBlank() }

            val postalCode: String? = plz.text.toString().takeIf { it.isNotBlank() }
            if (username.text.isEmpty()){
                Toast.makeText(this,getString(R.string.fillEditTexts), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val usernameString : String? = username.text.toString().takeIf { it.isNotBlank() }

            val cityString : String? = address.text.toString().takeIf { it.isNotBlank() }

            val descriptionString : String? = description.text.toString().takeIf { it.isNotBlank() }

            if (prename.text.isEmpty()){
                Toast.makeText(this,getString(R.string.fillEditTexts), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val prenameString : String = prename.text.toString()

            if (name.text.isEmpty()){
                Toast.makeText(this,getString(R.string.fillEditTexts), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val lastNameString : String = name.text.toString()

            val birthday : String? = datePickerFragment.toString()

            // Creating instance of the UserProfileAPI
            val userProfileApi = ProfileApi()

            userProfileApi.createUserProfile(null,usernameString,email,null,imageArray,descriptionString,password,
            null,null,null,streetString,"de",cityString,streetNrString,null,postalCode,prenameString, lastNameString,"profile")
            { profile, error ->

            if(error != null){


            }
            else if(profile != null){

            }
            }

            //Toast message if Registration was successful
            runOnUiThread {
                Toast.makeText(this@CreateUserProfileActivity, getString(R.string.profileCreated), Toast.LENGTH_SHORT).show()
            }
        }

        //Click Listener for the Cancel Button, returns to Registration Activity
        cancel.setOnClickListener {
            AlertDialog.Builder(this@CreateUserProfileActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent = Intent(this@CreateUserProfileActivity, RegisterAccountActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
        //Click Listener for uploadPicture Button
        upload.setOnClickListener {
                selectImageFromGallery()
            }

        birthdate.text = getCurrentDate()


        newFragment.setOnDatePickedListener { date ->
            birthdate.text = date
        }
        birthdate.apply {

            setOnClickListener {
                showDatePickerDialog(this)
            }
        }

    }

    private fun showDatePickerDialog(v: View) {
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun getCurrentDate(): String {
        var currentDateString = ""
        try {
            val currentDate = Calendar.getInstance().time
            val formatter = SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault())
            currentDateString = formatter.format(currentDate)
        } catch (e: java.lang.NullPointerException) {
            e.printStackTrace()
        }
        return currentDateString
    }

    private fun getCreationDate(): Date? {
        var currentDate: Date? = null
        try {
            currentDate = Calendar.getInstance().time
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return currentDate
    }
}
package de.hhn.softwarelabor.pawswipeapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
import de.hhn.softwarelabor.pawswipeapp.utils.Base64Utils


private const val DISCRIMINATOR = "shelter"
private const val COUNTRY = "de"
/**
 * This activity allows the user to create a new shelter profile.
 * @author Felix Kuhbier & Simon Remm
 */
class CreateShelterActivity : AppCompatActivity() {

    private lateinit var profileApi: ProfileApi
    private lateinit var shelterNameEditText: EditText
    private lateinit var homepageEditText: EditText
    private lateinit var postalCodeEditText: EditText
    private lateinit var shelterCityEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var openingHoursEditText: EditText
    private lateinit var streetEditText: EditText
    private lateinit var streetNumberEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var uploadImageButton: Button
    private var lat: Double = 0.0
    private var lon: Double = 0.0


    /**
     * On create
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shelter)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        profileApi = ProfileApi()

        shelterNameEditText = findViewById(R.id.shelterEditText)
        homepageEditText = findViewById(R.id.homepageEditText)
        postalCodeEditText = findViewById(R.id.plzEditText)
        shelterCityEditText = findViewById(R.id.shelterAddressEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        openingHoursEditText = findViewById(R.id.openingHoursEditText)
        streetEditText = findViewById(R.id.shelterStreetEditText)
        streetNumberEditText = findViewById(R.id.streetNumberEditText)
        imageView = findViewById(R.id.pictureView)
        uploadImageButton = findViewById(R.id.uploadPictureButton)

        val cancelButton: Button = findViewById(R.id.clearButton)
        val createButton: Button = findViewById(R.id.doneButton)

        val email: String = intent.getStringExtra("email").toString()
        val password: String = intent.getStringExtra("passwordHashed").toString()


        createButton.setOnClickListener {

            
            if ( // Check if the required Fields are empty
                checkEmpty(shelterNameEditText.text.toString()) ||
                checkEmpty(phoneNumberEditText.text.toString()) ||
                checkEmpty(postalCodeEditText.text.toString()) ||
                checkEmpty(shelterCityEditText.text.toString()) ||
                checkEmpty(streetEditText.text.toString()) ||
                checkEmpty(streetNumberEditText.text.toString())
            ){
                Toast.makeText(
                    this@CreateShelterActivity,
                    "Bitte alle Pflichtfelder ausfüllen.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
    
                var imageString: String? = null

            if (imageView.drawable != null) {
                val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap

                imageString = Base64Utils.encode(bitmap)
            }
    
    
                val shelterStreetString: String? =
                    streetEditText.text.toString().takeIf { it.isNotBlank() }
    
                val shelterStreetNrString: String? =
                    streetNumberEditText.text.toString().takeIf { it.isNotBlank() }
    
                val shelterNameString: String =
                    shelterNameEditText.text.toString().takeIf { it.isNotBlank() }.toString()
    
                val phoneNumberString: String? =
                    phoneNumberEditText.text.toString().takeIf { it.isNotBlank() }
    
                val openingHrsString: String? =
                    openingHoursEditText.text.toString().takeIf { it.isNotBlank() }
    
                val homepageString: String? =
                    homepageEditText.text.toString().takeIf { it.isNotBlank() }
    
                val shelterCityString: String? =
                    shelterCityEditText.text.toString().takeIf { it.isNotBlank() }
    
                val postalCodeString: String? =
                    postalCodeEditText.text.toString().takeIf { it.isNotBlank() }
    
    
                createShelterProfile(
                    shelterNameString, email, phoneNumberString, imageString, password,
                    openingHrsString, shelterStreetString, COUNTRY, shelterCityString,
                    shelterStreetNrString, homepageString, postalCodeString, lat, lon
                )
    
    
            }
            

        }

        //Click Listener for uploadPicture Button
        uploadImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }

        cancelButton.setOnClickListener {
            AlertDialog.Builder(this@CreateShelterActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent =
                        Intent(this@CreateShelterActivity, RegisterAccountActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }

    /**
     * Activity result launcher for picking an image from the gallery.
     */
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            imageView.setImageURI(imageUri)
        }
    }

    /**
     * Creates a shelter profile with the provided information.
     *
     * @param username Shelter's name.
     * @param email Shelter's email address.
     * @param phone_number Shelter's phone number.
     * @param profile_picture Shelter's profile picture in Base64 format.
     * @param password Shelter's hashed password.
     * @param opening_hours Shelter's opening hours.
     * @param street Shelter's street address.
     * @param country Shelter's country.
     * @param city Shelter's city.
     * @param street_number Shelter's street number.
     * @param homepage Shelter's homepage URL.
     * @param postal_code Shelter's postal code.
     */
    
    // Checks if the given String is empty or only spaces.
    private fun checkEmpty(string: String) : Boolean {
        val newString = string.replace(" ", "")
        if (newString == "")
            return true
        return false
    }
    
    private fun createShelterProfile(
        username: String,
        email: String,
        phone_number: String?,
        profile_picture: String?,
        password: String,
        opening_hours: String?,
        street: String?,
        country: String?,
        city: String?,
        street_number: String?,
        homepage: String?,
        postal_code: String?,
        lat: Double,
        lon: Double
    ) {


        profileApi.createUserProfile(
            null, username, email, phone_number,
            profile_picture, null, password, null, null, opening_hours,
            street, country, city, street_number, homepage, postal_code, "",
            "", lat, lon, DISCRIMINATOR
        ) { _, error ->

            runOnUiThread {
                if (error != null) {

                    Log.e("PawSwipe", error.message.toString())

                    Toast.makeText(
                        this@CreateShelterActivity,
                        "${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {


                    Toast.makeText(
                        this@CreateShelterActivity,
                        getString(R.string.profileCreated),
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent =
                        Intent(this@CreateShelterActivity, LoginActivity::class.java)
                    startActivity(intent)


                }
            }


        }
    }


}

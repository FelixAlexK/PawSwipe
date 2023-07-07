package de.hhn.softwarelabor.pawswipeapp


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
import de.hhn.softwarelabor.pawswipeapp.utils.Base64Utils
import de.hhn.softwarelabor.pawswipeapp.utils.DatePickerFragment

private const val PICK_IMAGE_REQUEST = 1
private const val DISCRIMINATOR = "profile"

/**
 * This activity allows the user to create a new user profile with various information.
 * It includes fields for personal details, address, and an optional profile picture.
 *
 * @author Felix Kuhbier & Leo Kalmbach & Simon Remm
 */
class CreateUserProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private lateinit var datePickerFragment: DatePickerFragment

    private var profileApi: ProfileApi = ProfileApi()
    private lateinit var scrollView: ScrollView
    private lateinit var firstNameEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var birthdateButton: Button
    private lateinit var addressEditText: EditText
    private lateinit var doneButton: Button
    private lateinit var cancelButton: Button
    private lateinit var uploadButton: Button
    private lateinit var descriptionEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var plzEditText: EditText
    private lateinit var streetEditText: EditText
    private lateinit var streetNrEditText: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user_profile)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val password: String = intent.getStringExtra("passwordHashed").toString()
        val email: String = intent.getStringExtra("email").toString()

        datePickerFragment = DatePickerFragment(
            this.getString(R.string.de_dateFormat),
            this@CreateUserProfileActivity
        )

        datePickerFragment.setOnDatePickedListener { date ->
            birthdateButton.text = date
        }

        scrollView = findViewById(R.id.createProfileScrollView)
        firstNameEditText = findViewById(R.id.prenameEditText)
        nameEditText = findViewById(R.id.nameEditText)
        birthdateButton = findViewById(R.id.userBirthdayButton)
        addressEditText = findViewById(R.id.addressEditText)
        doneButton = findViewById(R.id.doneUserButton)
        cancelButton = findViewById(R.id.clearUserButton)
        uploadButton = findViewById(R.id.uploadPictureButton)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        usernameEditText = findViewById(R.id.usernameEditText)
        plzEditText = findViewById(R.id.postalAddressEditText)
        streetEditText = findViewById(R.id.streetEditText)
        streetNrEditText = findViewById(R.id.houseNumberEditText)
        imageView = findViewById(R.id.pictureView)


        birthdateButton.apply {

            setOnClickListener {
                showDatePickerDialog(this)
            }
        }

        doneButton.setOnClickListener {


            if (
                checkTVEmpty(usernameEditText) ||
                checkTVEmpty(firstNameEditText) ||
                checkTVEmpty(nameEditText) ||
                birthdateButton.text.toString() == resources.getString(R.string.birthday_text)
            ) {
                Toast.makeText(
                    this@CreateUserProfileActivity,
                    "Bitte alle Pflichtfelder ausfüllen.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                var imageString: String? = null

                if (imageView.drawable != null) {
                    val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap

                    imageString = Base64Utils.encode(bitmap)
                }


                val streetString: String? =
                    streetEditText.text.toString().takeIf { it.isNotBlank() }

                val streetNrString: String? =
                    streetNrEditText.text.toString().takeIf { it.isNotBlank() }

                val postalCode: String? = plzEditText.text.toString().takeIf { it.isNotBlank() }

                if (usernameEditText.text.isEmpty()) {
                    Toast.makeText(this, getString(R.string.fillEditTexts), Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val usernameString: String =
                    usernameEditText.text.toString().takeIf { it.isNotBlank() }.toString()

                val cityString: String? = addressEditText.text.toString().takeIf { it.isNotBlank() }

                val descriptionString: String? =
                    descriptionEditText.text.toString().takeIf { it.isNotBlank() }

                if (firstNameEditText.text.isEmpty()) {
                    Toast.makeText(this, getString(R.string.fillEditTexts), Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                val firstNameString: String = firstNameEditText.text.toString()

                if (nameEditText.text.isEmpty()) {
                    Toast.makeText(this, getString(R.string.fillEditTexts), Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                val lastNameString: String = nameEditText.text.toString()

                val birthday: String? =
                    datePickerFragment.convertDateToServerCompatibleDate(birthdateButton.text.toString())


                createUserProfile(
                    usernameString,
                    email,
                    imageString,
                    descriptionString,
                    password,
                    birthday,
                    streetString,
                    "de",
                    cityString,
                    streetNrString,
                    postalCode,
                    firstNameString,
                    lastNameString
                )

            }

        }


        //Click Listener for the Cancel Button, returns to Registration Activity
        cancelButton.setOnClickListener {
            AlertDialog.Builder(this@CreateUserProfileActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent =
                        Intent(this@CreateUserProfileActivity, RegisterAccountActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
        //Click Listener for uploadPicture Button
        uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }

    }
    /**
     * Checks if a TextView is empty or contains only spaces.
     *
     * @param v The TextView to check.
     * @return true if the TextView is empty or contains only spaces, false otherwise.
     */
    private fun checkTVEmpty(v: TextView): Boolean {
        val newString = v.text.toString().replace(" ", "")
        if (newString == ""){
            v.error = "Bitte Ausfüllen"
            scrollView.requestChildFocus(v,v)
            return true
        }
        return false
    }

    /**
     * Handles the result of the image picker activity.
     * Sets the picked image to the imageView.
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
     * Creates a new user profile with the provided information and sends it to the server.
     *
     * @param username The user's chosen username.
     * @param email The user's email address.
     * @param profile_picture The user's profile picture encoded as a Base64 string (optional).
     * @param description The user's description (optional).
     * @param password The user's hashed password.
     * @param birthday The user's birthdate in server-compatible format (optional).
     * @param street The user's street name (optional).
     * @param country The user's country code (optional).
     * @param city The user's city (optional).
     * @param street_number The user's street number (optional).
     * @param postal_code The user's postal code (optional).
     * @param firstname The user's first name.
     * @param lastname The user's last name.
     */
    private fun createUserProfile(
        username: String,
        email: String,
        profile_picture: String?,
        description: String?,
        password: String,
        birthday: String?,
        street: String?,
        country: String?,
        city: String?,
        street_number: String?,
        postal_code: String?,
        firstname: String,
        lastname: String,
    ) {

        profileApi.createUserProfile(
            null, username, email, null,
            profile_picture, description, password, null, birthday, null,
            street, country, city, street_number, null, postal_code, firstname,
            lastname, null, null, DISCRIMINATOR
        ) { response, error ->

            runOnUiThread {
                if (error != null) {

                    Log.e("PawSwipe", error.message.toString())

                    Toast.makeText(
                        this@CreateUserProfileActivity,
                        "${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {


                    Toast.makeText(
                        this@CreateUserProfileActivity,
                        getString(R.string.profileCreated),
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent =
                        Intent(this@CreateUserProfileActivity, LoginActivity::class.java)
                    startActivity(intent)


                }
            }

        }


    }

    /**
     * Displays a date picker dialog for the user to choose their birthdate.
     *
     * @param v The view that triggers the date picker dialog.
     */
    private fun showDatePickerDialog(v: View) {
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

}
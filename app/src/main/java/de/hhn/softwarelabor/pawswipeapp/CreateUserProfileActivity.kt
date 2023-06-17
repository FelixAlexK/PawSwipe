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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
import de.hhn.softwarelabor.pawswipeapp.utils.DatePickerFragment
import java.io.ByteArrayOutputStream

private const val PICK_IMAGE_REQUEST = 1
private const val DISCRIMINATOR = "profile"
class CreateUserProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private lateinit var datePickerFragment: DatePickerFragment

    private lateinit var profileApi: ProfileApi

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

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user_profile)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        datePickerFragment = DatePickerFragment(this.getString(R.string.de_dateFormat), this)
        profileApi = ProfileApi()

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

        val password: String = intent.getStringExtra("passwordHashed").toString()
        val email: String = intent.getStringExtra("email").toString()


        datePickerFragment.setOnDatePickedListener { date ->
            birthdateButton.text = date
        }

        birthdateButton.apply {

            setOnClickListener {
                showDatePickerDialog(this)
            }
        }

        doneButton.setOnClickListener {


            var imageArray: Array<Byte>? = null

            if (imageView.drawable != null) {
                val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap

                // Convert Bitmap to byte array
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                // Convert the ByteArray to Array<Byte>
                imageArray = byteArray.toTypedArray()
            }


            val streetString: String? = streetEditText.text.toString().takeIf { it.isNotBlank() }

            val streetNrString: String? =
                streetNrEditText.text.toString().takeIf { it.isNotBlank() }

            val postalCode: String? = plzEditText.text.toString().takeIf { it.isNotBlank() }

            if (usernameEditText.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.fillEditTexts), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usernameString: String =
                usernameEditText.text.toString().takeIf { it.isNotBlank() }.toString()

            val cityString: String? = addressEditText.text.toString().takeIf { it.isNotBlank() }

            val descriptionString: String? =
                descriptionEditText.text.toString().takeIf { it.isNotBlank() }

            if (firstNameEditText.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.fillEditTexts), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val firstNameString: String = firstNameEditText.text.toString()

            if (nameEditText.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.fillEditTexts), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val lastNameString: String = nameEditText.text.toString()

            val birthday: String? =
                datePickerFragment.convertDateToServerCompatibleDate(birthdateButton.text.toString())


            createUserProfile(
                usernameString,
                email,
                imageArray,
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
            selectImageFromGallery()
        }

    }

    private fun createUserProfile(
        username: String,
        email: String,
        profile_picture: Array<Byte>?,
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
            lastname, DISCRIMINATOR
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

    private fun showDatePickerDialog(v: View) {
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

}
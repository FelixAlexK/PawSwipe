package de.hhn.softwarelabor.pawswipeapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi

private const val DISCRIMINATOR = "shelter"
private const val COUNTRY = "de"

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

        val cancelButton: Button = findViewById(R.id.clearButton)
        val createButton: Button = findViewById(R.id.doneButton)

        val email: String = intent.getStringExtra("email").toString()
        val password: String = intent.getStringExtra("passwordHashed").toString()


        createButton.setOnClickListener {


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
                shelterNameString, email, phoneNumberString, password,
                openingHrsString, shelterStreetString, COUNTRY, shelterCityString,
                shelterStreetNrString, homepageString, postalCodeString
            )


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

    private fun createShelterProfile(
        username: String,
        email: String,
        phone_number: String?,
        password: String,
        opening_hours: String?,
        street: String?,
        country: String?,
        city: String?,
        street_number: String?,
        homepage: String?,
        postal_code: String?,
    ) {


        profileApi.createUserProfile(
            null, username, email, phone_number,
            null, null, password, null, null, opening_hours,
            street, country, city, street_number, homepage, postal_code, "",
            "", DISCRIMINATOR
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

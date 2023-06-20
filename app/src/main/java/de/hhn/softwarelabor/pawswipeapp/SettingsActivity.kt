package de.hhn.softwarelabor.pawswipeapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
import de.hhn.softwarelabor.pawswipeapp.utils.DatePickerFragment


class SettingsActivity : AppCompatActivity() {

    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var deleteUserButton: Button
    private lateinit var userBirthdayButton: Button

    private lateinit var emailEditText: EditText
    private lateinit var userNameEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var postalCodeEditText: EditText
    private lateinit var streetEditText: EditText
    private lateinit var streetNrEditText: EditText
    private lateinit var descriptionEditText: EditText

    private lateinit var userProfileImageView: ImageView


    private lateinit var datePickerFragment: DatePickerFragment

    private val map: MutableMap<String, String> = mutableMapOf()

    private var id: Int = 0

    private val profileApi = ProfileApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        id = intent.getIntExtra("id", 0)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        datePickerFragment = DatePickerFragment(this.getString(R.string.de_dateFormat), this)

        cancelButton = findViewById(R.id.cancel_btn)
        saveButton = findViewById(R.id.save_btn)
        deleteUserButton = findViewById(R.id.deleteUserProfile)

        userBirthdayButton = findViewById(R.id.userBirthdayButton)

        emailEditText = findViewById(R.id.emailEditText)

        userNameEditText = findViewById(R.id.userNameEditText)

        firstNameEditText = findViewById(R.id.firstNameEditText)

        lastNameEditText = findViewById(R.id.nameEditText)

        cityEditText = findViewById(R.id.addressEditText)

        postalCodeEditText = findViewById(R.id.postalAddressEditText)

        streetEditText = findViewById(R.id.streetEditText)

        streetNrEditText = findViewById(R.id.houseNumberEditText)

        descriptionEditText = findViewById(R.id.userDescriptionMultiLineText)

        userProfileImageView = findViewById(R.id.userProfileImageView)


        cancelButton.setOnClickListener {
            AlertDialog.Builder(this@SettingsActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent = Intent(this@SettingsActivity, MatchActivity::class.java)
                    intent.putExtra("id", id)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }


        saveButton.setOnClickListener {

            if (userBirthdayButton.text != this.getString(R.string.birthday_text)) {
                val birthDayString =
                    datePickerFragment.convertDateToServerCompatibleDate(userBirthdayButton.text.toString())
                birthDayString?.let { setMap("birthday", it) }
            }


            val emailString = emailEditText.text.toString()
            setMap("email", emailString)


            val userNameString = userNameEditText.text.toString()
            setMap("username", userNameString)


            val firstNameString = firstNameEditText.text.toString()
            setMap("firstname", firstNameString)


            val lastNameString = lastNameEditText.text.toString()
            setMap("lastname", lastNameString)


            val cityString = cityEditText.text.toString()
            setMap("city", cityString)


            val postalCodeString = postalCodeEditText.text.toString()
            setMap("postal_code", postalCodeString)


            val streetString = streetEditText.text.toString()
            setMap("street", streetString)


            val streetNrString = streetNrEditText.text.toString()
            setMap("street_number", streetNrString)


            val descriptionString = descriptionEditText.text.toString()
            setMap("description", descriptionString)

            AlertDialog.Builder(this@SettingsActivity)
                .setTitle(getString(R.string.saveChanges_headerText))
                .setMessage(getString(R.string.saveChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->

                    updateUser()
                    val intent = Intent(this@SettingsActivity, MatchActivity::class.java)
                    intent.putExtra("id", id)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        deleteUserButton.setOnClickListener {

            AlertDialog.Builder(this@SettingsActivity)
                .setTitle(getString(R.string.deleteProfile_headerText))
                .setMessage(getString(R.string.deleteProfile_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->

                    deleteUser()
                    val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }



        datePickerFragment.setOnDatePickedListener { date ->
            userBirthdayButton.text = date
        }
        userBirthdayButton.apply {

            setOnClickListener {
                showDatePickerDialog(this)
            }
        }
    }

    private fun updateUser() {

        profileApi.updateUserProfileByID(id, map) { response, error ->
            if (error != null) {
                runOnUiThread {
                    Toast.makeText(
                        this@SettingsActivity,
                        "Nutzer konnte nicht aktualisiert werden",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (response != null) {
                runOnUiThread {
                    Toast.makeText(
                        this@SettingsActivity,
                        "Nutzer wurde aktualisiert",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun deleteUser() {
        profileApi.deleteUserProfileByID(id) { response, error ->
            if (error != null) {
                runOnUiThread {
                    Toast.makeText(
                        this@SettingsActivity,
                        "Nutzer konnte nicht gelöscht werden",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (response != null) {
                runOnUiThread {
                    Toast.makeText(
                        this@SettingsActivity,
                        "Nutzer wurde gelöscht",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun setMap(key: String, value: String) {

        if (value.isNotEmpty() || value.isNotBlank()) {
            map[key] = value
        }

    }

    private fun showDatePickerDialog(v: View) {
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

}
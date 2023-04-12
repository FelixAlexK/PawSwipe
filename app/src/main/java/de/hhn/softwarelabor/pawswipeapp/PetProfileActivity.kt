package de.hhn.softwarelabor.pawswipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PetProfileActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner

    private lateinit var addPictureButton: Button
    private lateinit var cancelButton: Button
    private lateinit var createPetButton: Button

    private lateinit var petNameEditText: EditText
    private lateinit var speciesEditText: EditText
    private lateinit var breedEditText: EditText
    private lateinit var petBirthdayButton: Button
    private lateinit var petColorEditText: EditText
    private lateinit var petIllnessMultilineText: EditText
    private var newFragment: DatePickerFragment = DatePickerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_profil)

        init()


        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }


        createPetButton.setOnClickListener {
            checkEditTextInput(petNameEditText.text.toString())
            checkMultilineTextInput(petIllnessMultilineText.text.toString())

        }

        petBirthdayButton.apply {
            setOnClickListener {
                showDatePickerDialog(this)
            }
        }

    }

    private fun showDatePickerDialog(v: View) {
        newFragment.show(supportFragmentManager, "datePicker")

    }

    private fun checkEditTextInput(input: String): Boolean {
        if (input.length <= EDIT_TEXT_LENGTH) {
            return true
        }

        createToast("input too long, max. length: $EDIT_TEXT_LENGTH")
        return false
    }

    private fun checkMultilineTextInput(input: String): Boolean {
        if (input.length <= MULTILINE_TEXT_LENGTH) {
            return true
        }

        createToast("input too long, max. length: $MULTILINE_TEXT_LENGTH")
        return false
    }


    private fun createToast(message: String) {
        Toast.makeText(this@PetProfileActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentDate(): String{
        var currentDateString = ""
        try {
            val currentDate = Calendar.getInstance().time
            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            currentDateString = formatter.format(currentDate)
        }catch (e: java.lang.NullPointerException){
            e.printStackTrace()
        }
        return currentDateString
    }

    private fun init() {
        try {
            spinner = findViewById(R.id.petGenderSpinner)

            addPictureButton = findViewById(R.id.addPetProfileImageButton)
            cancelButton = findViewById(R.id.cancelButton)
            createPetButton = findViewById(R.id.createButton)

            petBirthdayButton = findViewById(R.id.petBirthdayButton)
            petBirthdayButton.text = getCurrentDate()

            petNameEditText = findViewById(R.id.petNameEditText)
            speciesEditText = findViewById(R.id.petSpeciesEditText)
            breedEditText = findViewById(R.id.petBreedsEditText)
            petColorEditText = findViewById(R.id.petColorEditText)

            petIllnessMultilineText = findViewById(R.id.petPreExistingIllnessMultiLineText)

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }


    companion object {
        private const val DATE_FORMAT = "dd.MM.yyyy"
        private const val EDIT_TEXT_LENGTH = 20
        private const val MULTILINE_TEXT_LENGTH = 80
    }


}
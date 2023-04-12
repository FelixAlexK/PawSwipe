package de.hhn.softwarelabor.pawswipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.System.DATE_FORMAT
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import org.w3c.dom.Element
import java.text.DateFormat
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
    private lateinit var petBirthdayEditText: EditText
    private lateinit var petColorEditText: EditText
    private lateinit var petIllnessMultilineText: EditText


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


        createPetButton.setOnClickListener{_ ->
            checkEditTextInput(petNameEditText.text.toString())
            checkMultilineTextInput(petIllnessMultilineText.text.toString())
            ckeckDateFormat(petBirthdayEditText.text.toString())
        }

    }

    private fun checkEditTextInput(input: String): Boolean{
        if(input.length <= EDIT_TEXT_LENGTH){
            return true
        }

        createToast("input too long, max. length: $EDIT_TEXT_LENGTH")
        return false
    }

    private fun checkMultilineTextInput(input: String): Boolean{
        if(input.length <= MULTILINE_TEXT_LENGTH){
            return true
        }

        createToast("input too long, max. length: $MULTILINE_TEXT_LENGTH")
        return false
    }

    private fun ckeckDateFormat(dateString: String): Boolean{
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        formatter.isLenient = false

        return try {
            formatter.parse(dateString)
            true
        }catch (e: ParseException){
            createToast("The following format is desired: $DATE_FORMAT")
            false
        }
    }

    private fun createToast(message: String){
        Toast.makeText(this@PetProfileActivity, message, Toast.LENGTH_SHORT ).show()
    }

    private fun init(){
        try {
            spinner = findViewById(R.id.petGenderSpinner)

            addPictureButton = findViewById(R.id.addPetProfileImageButton)
            cancelButton = findViewById(R.id.cancelButton)
            createPetButton = findViewById(R.id.createButton)

            petNameEditText = findViewById(R.id.petNameEditText)
            speciesEditText = findViewById(R.id.petSpeciesEditText)
            breedEditText = findViewById(R.id.petBreedsEditText)
            petBirthdayEditText = findViewById(R.id.petBirthdayEditText)
            petColorEditText = findViewById(R.id.petColorEditText)
            petIllnessMultilineText = findViewById(R.id.petPreExistingIllnessMultiLineText)
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }catch (e: NullPointerException){
            e.printStackTrace()
        }

    }


    companion object{
        private const val DATE_FORMAT = "dd.MM.yyyy"
        private const val EDIT_TEXT_LENGTH = 20
        private const val MULTILINE_TEXT_LENGTH = 80
    }


}
package de.hhn.softwarelabor.pawswipeapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.AnimalProfileApi
import de.hhn.softwarelabor.pawswipeapp.api.UserProfileApi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PetProfileActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner

    private lateinit var addPictureButton: Button
    private lateinit var cancelPetButton: Button
    private lateinit var createPetButton: Button

    private lateinit var petNameEditText: EditText
    private lateinit var speciesEditText: EditText
    private lateinit var breedEditText: EditText
    private lateinit var petBirthdayButton: Button
    private lateinit var petColorEditText: EditText
    private lateinit var petIllnessMultilineText: EditText
    private lateinit var petDescriptionText: EditText


    private var newFragment: DatePickerFragment = DatePickerFragment()
    private var animalProfile: AnimalProfileApi = AnimalProfileApi()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_profil)

        init()

        createPetButton.setOnClickListener {

            createPet(
                petNameEditText.text.toString(),
                speciesEditText.text.toString(),
                newFragment.toString(),
                petIllnessMultilineText.text.toString(),
                petDescriptionText.text.toString(),
                breedEditText.text.toString(),
                petColorEditText.text.toString(),
                spinner.selectedItem.toString(),
                38
            )

        }

        cancelPetButton.setOnClickListener {
            petNameEditText.setText("")
            speciesEditText.setText("")
            petIllnessMultilineText.setText("")
            petDescriptionText.setText("")
            breedEditText.setText("")
            petColorEditText.setText("")
        }

        newFragment.setOnDatePickedListener { date ->
            petBirthdayButton.text = date
        }


        petBirthdayButton.apply {

            setOnClickListener {
                showDatePickerDialog(this)
            }
        }

    }

    private fun createPet(
        name: String?, species: String?, birthday: String?, illness: String?,
        description: String?, breed: String?, color: String?, gender: String?,
        profileId: Int
    ) {

        val userProfile = UserProfileApi()

        try {

            userProfile.getUserProfileByID(38) { profile,_, error ->


                if (error != null) {
                    Log.e("AnimalProfileApi", "Error fetching: $error")
                } else if (profile != null) {
                    animalProfile.createAnimalProfile(
                        name, species, birthday!!, illness, description, breed, color, gender, profile
                    ) { profile, error ->

                        if (error != null) {
                            Log.e("AnimalProfileApi", "Error fetching: $error")
                        } else if (profile != null) {
                            Log.d("AnimalProfileApi", "Successful: $profile")
                        }
                    }
                }
        }



        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    private fun showDatePickerDialog(v: View) {
        newFragment.show(supportFragmentManager, "datePicker")

    }


    private fun createToast(message: String) {
        Toast.makeText(this@PetProfileActivity, message, Toast.LENGTH_SHORT).show()
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

    private fun init() {
        try {
            spinner = findViewById(R.id.petGenderSpinner)

            addPictureButton = findViewById(R.id.addPetProfileImageButton)
            cancelPetButton = findViewById(R.id.cancel_btn)
            createPetButton = findViewById(R.id.petCreateButton)

            petBirthdayButton = findViewById(R.id.petBirthdayButton)
            petBirthdayButton.text = getCurrentDate()

            petNameEditText = findViewById(R.id.petNameEditText)
            speciesEditText = findViewById(R.id.petSpeciesEditText)
            breedEditText = findViewById(R.id.petBreedEditText)
            petColorEditText = findViewById(R.id.petColorEditText)
            petDescriptionText = findViewById(R.id.petDescriptionMultiLineText)

            petIllnessMultilineText = findViewById(R.id.petPreExistingIllnessMultiLineText)

            ArrayAdapter.createFromResource(
                this, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinner.adapter = adapter
            }

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    fun test(){
        val userProfileApi = UserProfileApi()

        userProfileApi.getUserProfileByID(38) { _,profile, error ->

            if(error != null){
                //handle error
            }else if(profile != null){
                //handle response
            }

        }
    }



    companion object {
        private const val EDIT_TEXT_LENGTH = 20
        private const val MULTILINE_TEXT_LENGTH = 80
    }


}
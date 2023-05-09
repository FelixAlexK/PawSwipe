package de.hhn.softwarelabor.pawswipeapp


import android.app.AlertDialog
import android.content.Intent
import android.content.ContentValues.TAG
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
import de.hhn.softwarelabor.pawswipeapp.api.animal.AnimalProfileApi
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
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


    private var datePickerFragment: DatePickerFragment = DatePickerFragment()
    private var animalProfile: AnimalProfileApi = AnimalProfileApi()
    private var profileId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_profil)

        init()

        profileId = 11
        createPetButton.setOnClickListener {
            if (petDescriptionText.length() <= MULTILINE_TEXT_LENGTH){
                profileId?.let {
                    createPet(
                        petNameEditText.text.toString(),
                        speciesEditText.text.toString(),
                        datePickerFragment.toString(),
                        petIllnessMultilineText.text.toString(),
                        petDescriptionText.text.toString(),
                        breedEditText.text.toString(),
                        petColorEditText.text.toString(),
                        spinner.selectedItem.toString(),
                        it
                    )
                }?: run {
                    Toast.makeText(this, "Die ID des Benutzerprofils ist ungültig. Bitte versuchen Sie es erneut.", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Profile ID is null")
                }
            }else {
                runOnUiThread{
                    Toast.makeText(this, "Beschreibung ist zu lang: ${petDescriptionText.length()}/50", Toast.LENGTH_SHORT).show()
                }
            }


        }

        cancelPetButton.setOnClickListener {
            AlertDialog.Builder(this@PetProfileActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    petNameEditText.setText("")
                    speciesEditText.setText("")
                    petIllnessMultilineText.setText("")
                    petDescriptionText.setText("")
                    breedEditText.setText("")
                    petColorEditText.setText("")
                    val intent = Intent(this@PetProfileActivity, ChatActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        datePickerFragment.setOnDatePickedListener { date ->
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
        profile_id: Int
    ) {

        val profile = ProfileApi()



        profile.getUserProfileByID(profile_id) { user, error ->


            if (error != null) {
                Log.e(TAG, error.message.toString())
                runOnUiThread{
                    Toast.makeText(this, "Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.", Toast.LENGTH_SHORT).show()
                }
            } else if (user != null) {
                animalProfile.createAnimalProfile(
                    name, species, birthday, illness, description, breed, color, gender, user
                ) { profile, error ->

                    if (error != null) {
                        Log.e(TAG, error.message.toString())
                        runOnUiThread{
                            Toast.makeText(this, "Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.", Toast.LENGTH_SHORT).show()
                        }
                    } else if (profile != null) {
                        Log.d(TAG, "Successful: $profile")
                        runOnUiThread{
                            Toast.makeText(this, "Profil für $name wurde erfolgreich erstellt!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


    }


    private fun showDatePickerDialog(v: View) {
        datePickerFragment.show(supportFragmentManager, "datePicker")

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
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message.toString())
            e.printStackTrace()
        }
        return currentDateString
    }

    private fun init() {
        try {
            spinner = findViewById(R.id.petGenderSpinner)

            addPictureButton = findViewById(R.id.addPetProfileImageButton)
            cancelPetButton = findViewById(R.id.cancel_btn)
            createPetButton = findViewById(R.id.save_btn)

            petBirthdayButton = findViewById(R.id.petBirthdayButton)
            petBirthdayButton.text = getCurrentDate()

            petNameEditText = findViewById(R.id.petNameEditText)
            speciesEditText = findViewById(R.id.petSpeciesEditText)
            breedEditText = findViewById(R.id.petBreedsEditText)
            petColorEditText = findViewById(R.id.petColorEditText)
            petDescriptionText = findViewById(R.id.petDescriptionMultiLineText)

            petIllnessMultilineText = findViewById(R.id.petPreExistingIllnessMultiLineText)

            ArrayAdapter.createFromResource(
                this, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinner.adapter = adapter
            }

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message.toString())
            e.printStackTrace()
        }

    }


    companion object {
        private const val EDIT_TEXT_LENGTH = 20
        private const val MULTILINE_TEXT_LENGTH = 255
    }


}
package de.hhn.softwarelabor.pawswipeapp


import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.animal.AnimalProfileApi
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi

class PetProfileActivity : AppCompatActivity() {

    private lateinit var genderSpinner: Spinner
    private lateinit var speciesSpinner: Spinner
    private lateinit var breedSpinner: Spinner

    private lateinit var addPictureButton: Button
    private lateinit var cancelPetButton: Button
    private lateinit var createPetButton: Button

    private lateinit var petNameEditText: EditText
    private lateinit var petBirthdayButton: Button
    private lateinit var petColorEditText: EditText
    private lateinit var petIllnessMultilineText: EditText
    private lateinit var petDescriptionText: EditText


    private lateinit var datePickerFragment: DatePickerFragment
    private var animalProfile: AnimalProfileApi = AnimalProfileApi()
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_profil)
        datePickerFragment =
            DatePickerFragment(this.getString(R.string.de_dateFormat), this@PetProfileActivity)
        init()

        id = intent.getIntExtra("id", 0)
        createPetButton.setOnClickListener {
            if (petDescriptionText.length() <= MULTILINE_TEXT_LENGTH) {

                val birthday =
                    datePickerFragment.convertDateToServerCompatibleDate(petBirthdayButton.text.toString())
                createPet(
                    petNameEditText.text.toString(),
                    speciesSpinner.selectedItem.toString(),
                    birthday,
                    petIllnessMultilineText.text.toString(),
                    petDescriptionText.text.toString(),
                    breedSpinner.selectedItem.toString(),
                    petColorEditText.text.toString(),
                    genderSpinner.selectedItem.toString(),
                    id
                )

            } else {
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Beschreibung ist zu lang: ${petDescriptionText.length()}/50",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }

        cancelPetButton.setOnClickListener {
            AlertDialog.Builder(this@PetProfileActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    petNameEditText.setText("")
                    petIllnessMultilineText.setText("")
                    petDescriptionText.setText("")
                    petColorEditText.setText("")
                    val intent = Intent(this@PetProfileActivity, MatchActivity::class.java)
                    intent.putExtra("id", id)
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
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (user != null) {
                animalProfile.createAnimalProfile(
                    name,
                    species,
                    birthday,
                    illness,
                    description,
                    breed,
                    color,
                    gender,
                    null,
                    null,
                    null,
                    null,
                    null,
                    user
                ) { profile, error ->

                    if (error != null) {
                        Log.e(TAG, error.message.toString())
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else if (profile != null) {
                        Log.d(TAG, "Successful: $profile")
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "Profil für $name wurde erfolgreich erstellt!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val intent = Intent(this@PetProfileActivity, MatchActivity::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                    }
                }
            }
        }


    }


    private fun showDatePickerDialog(v: View) {
        datePickerFragment.show(supportFragmentManager, "datePicker")

    }


    private fun init() {
        try {

            addPictureButton = findViewById(R.id.addPetProfileImageButton)
            cancelPetButton = findViewById(R.id.cancel_btn)
            createPetButton = findViewById(R.id.save_btn)

            petBirthdayButton = findViewById(R.id.petBirthdayButton)
            petNameEditText = findViewById(R.id.petNameEditText)
            petColorEditText = findViewById(R.id.petColorEditText)
            petDescriptionText = findViewById(R.id.petDescriptionMultiLineText)

            petIllnessMultilineText = findViewById(R.id.petPreExistingIllnessMultiLineText)

            genderSpinner = findViewById(R.id.petGenderSpinner)
            ArrayAdapter.createFromResource(
                this, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                genderSpinner.adapter = adapter
            }

            speciesSpinner = findViewById(R.id.petProfileSpeciesSpinner)
            ArrayAdapter.createFromResource(
                this,
                R.array.species_array,
                android.R.layout.simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                speciesSpinner.adapter = adapter
            }

            speciesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    breedSpinner = findViewById(R.id.petProfileBreedsSpinner)
                    when (parent.getItemAtPosition(position).toString()) {
                        "Hund" -> {
                            ArrayAdapter.createFromResource(
                                breedSpinner.context,
                                R.array.dog_array,
                                android.R.layout.simple_spinner_dropdown_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                breedSpinner.adapter = adapter
                            }
                        }
                        "Katze" -> {
                            ArrayAdapter.createFromResource(
                                breedSpinner.context,
                                R.array.cat_array,
                                android.R.layout.simple_spinner_dropdown_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                breedSpinner.adapter = adapter
                            }
                        }
                        "Vogel" -> {
                            ArrayAdapter.createFromResource(
                                breedSpinner.context,
                                R.array.bird_array,
                                android.R.layout.simple_spinner_dropdown_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                breedSpinner.adapter = adapter
                            }
                        }
                        "Kleintiere" -> {
                            ArrayAdapter.createFromResource(
                                breedSpinner.context,
                                R.array.smallAnimal_array,
                                android.R.layout.simple_spinner_dropdown_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                breedSpinner.adapter = adapter
                            }
                        }
                        "Reptilien" -> {
                            ArrayAdapter.createFromResource(
                                breedSpinner.context,
                                R.array.reptile_array,
                                android.R.layout.simple_spinner_dropdown_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                breedSpinner.adapter = adapter
                            }
                        }
                        else -> {
                            ArrayAdapter.createFromResource(
                                breedSpinner.context,
                                R.array.breed_array,
                                android.R.layout.simple_spinner_dropdown_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                breedSpinner.adapter = adapter
                            }
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Handle the case where no item is selected
                }
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
package de.hhn.softwarelabor.pawswipeapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.utils.DatePickerFragment

class EditAnimalActivity : AppCompatActivity() {

    private lateinit var genderSpinner: Spinner
    private lateinit var speciesSpinner: Spinner
    private lateinit var breedSpinner: Spinner

    private lateinit var datePickerFragment: DatePickerFragment

    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var deletePetProfile: Button
    private lateinit var petBirthdayButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_animal)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        genderSpinner = findViewById(R.id.petGenderSpinner)

        datePickerFragment = DatePickerFragment(this.getString(R.string.de_dateFormat), this)

        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }

        speciesSpinner = findViewById(R.id.petSpeciesSpinner)
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
                breedSpinner = findViewById(R.id.petBreedsSpinner)
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



        cancelButton = findViewById(R.id.cancel_btn)
        cancelButton.setOnClickListener {
            AlertDialog.Builder(this@EditAnimalActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        saveButton = findViewById(R.id.save_btn)
        saveButton.setOnClickListener {

            AlertDialog.Builder(this@EditAnimalActivity)
                .setTitle(getString(R.string.saveChanges_headerText))
                .setMessage(getString(R.string.saveChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        deletePetProfile = findViewById(R.id.deletePetProfile)
        deletePetProfile.setOnClickListener {

            AlertDialog.Builder(this@EditAnimalActivity)
                .setTitle(getString(R.string.deleteProfile_headerText))
                .setMessage(getString(R.string.deleteProfile_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->


                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        petBirthdayButton = findViewById(R.id.petBirthdayButton)

        datePickerFragment.setOnDatePickedListener { date ->
            petBirthdayButton.text = date
        }
        petBirthdayButton.apply {

            setOnClickListener {
                showDatePickerDialog(this)
            }
        }
    }
    /**
     * Displays a date picker dialog.
     *
     * @param v The view that triggers the date picker dialog.
     */
    private fun showDatePickerDialog(v: View) {
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

}
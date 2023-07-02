package de.hhn.softwarelabor.pawswipeapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.utils.*
import android.widget.Switch



class FilterActivity : AppCompatActivity() {

    private var species: String = "---"
    private var illness: String = "---"
    private var breed: String = "---"
    private var color: String = "---"
    private var gender: String = "---"
    private var minAge: String = "---"
    private var maxAge: String = "---"

    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var resetFilterButton: Button

    private lateinit var genderSpinner: Spinner
    private lateinit var speciesSpinner: Spinner
    private lateinit var breedSpinner: Spinner

    private lateinit var minAgeSpinner: Spinner
    private lateinit var maxAgeSpinner: Spinner


    private lateinit var datePickerFragment: DatePickerFragment

    private lateinit var radiusEditText: EditText
    private lateinit var petColorEditText: EditText
    private lateinit var petIllnessSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        speciesSpinner = findViewById(R.id.petSpeciesFilter)
        petIllnessSwitch = findViewById(R.id.illness_switch)
        breedSpinner = findViewById(R.id.petBreedsFilter)
        petColorEditText = findViewById(R.id.petColorEditText)
        genderSpinner = findViewById(R.id.petGenderSpinner)



        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        datePickerFragment =
            DatePickerFragment(this.getString(R.string.de_dateFormat), this)

        cancelButton = findViewById(R.id.cancel_btn2)
        cancelButton.setOnClickListener {
            AlertDialog.Builder(this@FilterActivity)
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

        saveButton = findViewById(R.id.save_btn2)
        saveButton.setOnClickListener {

            if(speciesSpinner.selectedItemPosition == 0){
                species = speciesSpinner.selectedItem.toString()
            }
            if(petIllnessSwitch.isActivated){
                illness = ""
            }
            if(breedSpinner.selectedItemPosition == 0){
                breed = breedSpinner.selectedItem.toString()
            }
            if(!(petColorEditText.text.equals("Keine"))){
                color = petColorEditText.text.toString()
            }
            if(genderSpinner.selectedItemPosition == 0){
                gender = genderSpinner.selectedItem.toString()
            }
            if(minAgeSpinner.selectedItemPosition == 0){
                minAge = minAgeSpinner.selectedItem.toString()
            }
            if(maxAgeSpinner.selectedItemPosition == 0){
                maxAge = maxAgeSpinner.selectedItem.toString()
            }


            println("Tierart: $species \nRasse: $breed \nGeschlecht: $gender \nMin Alter: $minAge \nMax Alter: $maxAge \nFarbe: $color")

        }

        minAgeSpinner = findViewById(R.id.petMinAgeFilter)
        ArrayAdapter.createFromResource(
            this,
            R.array.age_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            minAgeSpinner.adapter = adapter
        }

        maxAgeSpinner = findViewById(R.id.petMaxAgeFilter)
        ArrayAdapter.createFromResource(
            this,
            R.array.age_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            maxAgeSpinner.adapter = adapter
        }



        genderSpinner = findViewById(R.id.petGenderSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            genderSpinner.adapter = adapter
        }


        radiusEditText = findViewById(R.id.radius_et)
        petColorEditText = findViewById(R.id.petColorEditText)
        resetFilterButton = findViewById(R.id.resetFilter_btn)

        speciesSpinner = findViewById(R.id.petSpeciesFilter)
        ArrayAdapter.createFromResource(
            this,
            R.array.species_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            speciesSpinner.adapter = adapter
        }

        petIllnessSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Switch is activated
                petIllnessSwitch.text = "Ok"
            } else {
                // Switch is deactivated
                petIllnessSwitch.text = "Nicht Ok"
            }
        }

        speciesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                breedSpinner = findViewById(R.id.petBreedsFilter)
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

        resetFilterButton.setOnClickListener {
            radiusEditText.setText("")
            speciesSpinner.setSelection(0)
            breedSpinner.setSelection(0)
            genderSpinner.setSelection(0)
            petColorEditText.setText("")
        }
    }

    private fun showDatePickerDialog(v: View) {
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

}
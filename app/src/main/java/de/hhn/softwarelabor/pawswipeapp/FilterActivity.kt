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

    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var resetFilterButton: Button

    private lateinit var genderSpinner: Spinner
    private lateinit var speciesSpinner: Spinner
    private lateinit var breedSpinner: Spinner
    private lateinit var minAgeSpinner: Spinner
    private lateinit var maxAgeSpinner: Spinner

    private lateinit var radiusField: EditText
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
        minAgeSpinner = findViewById(R.id.petMinAgeFilter)
        maxAgeSpinner = findViewById(R.id.petMaxAgeFilter)
        radiusField = findViewById(R.id.radius_field)
        petColorEditText = findViewById(R.id.petColorEditText)
        resetFilterButton = findViewById(R.id.resetFilter_btn)
        saveButton = findViewById(R.id.save_btn2)
        cancelButton = findViewById(R.id.cancel_btn2)
        breedSpinner = findViewById(R.id.petBreedsFilter)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        setupListeners()
        setupSpinners()         // also loads Data from AppData
        loadTextViews()         // also loads Data from AppData
    }

    private fun setupListeners(){

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

        saveButton.setOnClickListener {

            // saves the filter settings into the AppData companion object
            AppData.setRadius(radiusField.text.toString().toInt())

            if(speciesSpinner.selectedItemPosition != 0){ // if not selected first position in list which represents a place holder
                AppData.setSpecies(speciesSpinner.selectedItem.toString())
            }
            else{ AppData.setSpecies("") }

            if(petIllnessSwitch.isChecked){
                AppData.setIllness(true)
            }
            else{ AppData.setIllness(false) }

            if(breedSpinner.selectedItemPosition != 0){
                AppData.setBreed(breedSpinner.selectedItem.toString())
            }
            else{ AppData.setBreed("") }

            AppData.setColor(petColorEditText.text.toString())

            if(genderSpinner.selectedItemPosition != 0){
                AppData.setGender(genderSpinner.selectedItem.toString())
            }
            else{ AppData.setGender("") }

            if(minAgeSpinner.selectedItemPosition != 0){
                AppData.setMinAge(minAgeSpinner.selectedItem.toString())
            }
            else{ AppData.setMinAge("") }

            if(maxAgeSpinner.selectedItemPosition != 0){
                AppData.setMaxAge(maxAgeSpinner.selectedItem.toString())
            }
            else{ AppData.setMaxAge("") }

            // This is for debugging @todo remove this part
            val radius = AppData.getRadius()
            val species = AppData.getSpecies()
            val breed = AppData.getBreed()
            val gender = AppData.getGender()
            val minAge = AppData.getMinAge()
            val maxAge = AppData.getMaxAge()
            val color = AppData.getColor()
            val illness = AppData.getIllness()
            println("Radius: $radius \nTierart: $species \nRasse: $breed \nGeschlecht: $gender \nMin Alter: $minAge \nMax Alter: $maxAge \nFarbe: $color \nIllness: $illness")

        }

        // Listener for illness switch
        petIllnessSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                petIllnessSwitch.text = "Ok"
            } else {
                petIllnessSwitch.text = "Nicht Ok"
            }
        }

        // Listener for reset button
        resetFilterButton.setOnClickListener {
            speciesSpinner.setSelection(0)
            breedSpinner.setSelection(0)
            minAgeSpinner.setSelection(0)
            maxAgeSpinner.setSelection(0)
            genderSpinner.setSelection(0)
            petIllnessSwitch.isChecked = true
            petIllnessSwitch.text = "Ok"
            petColorEditText.setText("")
            radiusField.setText("0")
        }
    }

    private fun loadTextViews(){
        // Load the color from AppData
        val color: String = AppData.getColor()
        if(!(color.equals(""))){
            petColorEditText.setText(color)
        }

        //Load the radius from AppData
        val radius: Int = AppData.getRadius()
        if(radius == 0){
            radiusField.setText("")
        }
        else{
            radiusField.setText(radius.toString())
        }

        // Loads the illness from AppData
        val illness :Boolean = AppData.getIllness()
        if(illness){
            petIllnessSwitch.text = "Ok"
            petIllnessSwitch.isChecked = true
        }
        else{
            petIllnessSwitch.text = "Nicht Ok"
            petIllnessSwitch.isChecked = false
        }
    }

    private fun setupSpinners(){

        ArrayAdapter.createFromResource(
            this,
            R.array.age_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            minAgeSpinner.adapter = adapter
            val position = (0 until adapter.count).firstOrNull { adapter.getItem(it) == AppData.getMinAge() }
            if (position != null) {
                minAgeSpinner.setSelection(position)
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.age_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            maxAgeSpinner.adapter = adapter
            val position = (0 until adapter.count).firstOrNull { adapter.getItem(it) == AppData.getMaxAge() }
            if (position != null) {
                maxAgeSpinner.setSelection(position)
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            genderSpinner.adapter = adapter
            val position = (0 until adapter.count).firstOrNull { adapter.getItem(it) == AppData.getGender() }
            if (position != null) {
                genderSpinner.setSelection(position)
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.species_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            speciesSpinner.adapter = adapter
            val species = AppData.getSpecies();
            val position = (0 until adapter.count).firstOrNull { adapter.getItem(it) == species }
            if (position != null) {
                speciesSpinner.setSelection(position)
            }
        }

        speciesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val breed = AppData.getBreed()

                when (parent.getItemAtPosition(position).toString()) {
                    "Hund" -> {
                        ArrayAdapter.createFromResource(
                            breedSpinner.context,
                            R.array.dog_array,
                            android.R.layout.simple_spinner_dropdown_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            breedSpinner.adapter = adapter

                            val position = (0 until adapter.count).firstOrNull { adapter.getItem(it) == breed }
                            if (position != null) {
                                breedSpinner.setSelection(position)
                            }
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

                            val position = (0 until adapter.count).firstOrNull { adapter.getItem(it) == breed }
                            if (position != null) {
                                breedSpinner.setSelection(position)
                            }
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

                            val position = (0 until adapter.count).firstOrNull { adapter.getItem(it) == breed }
                            if (position != null) {
                                breedSpinner.setSelection(position)
                            }
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

                            val position = (0 until adapter.count).firstOrNull { adapter.getItem(it) == breed }
                            if (position != null) {
                                breedSpinner.setSelection(position)
                            }
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

                            val position = (0 until adapter.count).firstOrNull { adapter.getItem(it) == breed }
                            if (position != null) {
                                breedSpinner.setSelection(position)
                            }
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
    }
}
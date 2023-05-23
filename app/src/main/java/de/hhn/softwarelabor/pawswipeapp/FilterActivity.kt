package de.hhn.softwarelabor.pawswipeapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import java.text.SimpleDateFormat
import java.util.*

class FilterActivity : AppCompatActivity() {

    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var spinner: Spinner
    private var newFragment: DatePickerFragment = DatePickerFragment()
    private lateinit var petBirthdayButton: Button
    private lateinit var resetFilterButton: Button
    private lateinit var radiusEditText: EditText
    private lateinit var petSpeciesEditText: EditText
    private lateinit var petBreedEditText: EditText
    private lateinit var petColorEditText: EditText
    private lateinit var petIllnessEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        cancelButton = findViewById(R.id.cancel_btn2)
        cancelButton.setOnClickListener {
            AlertDialog.Builder(this@FilterActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent = Intent(this@FilterActivity, ChatActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        saveButton = findViewById(R.id.save_btn2)
        saveButton.setOnClickListener {

            AlertDialog.Builder(this@FilterActivity)
                .setTitle(getString(R.string.saveChanges_headerText))
                .setMessage(getString(R.string.saveChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent = Intent(this@FilterActivity, ChatActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        spinner = findViewById(R.id.petGenderSpinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }

        petBirthdayButton = findViewById(R.id.petBirthdayButton)

        newFragment.setOnDatePickedListener { date ->
            petBirthdayButton.text = date
        }
        petBirthdayButton.apply {

            setOnClickListener {
                showDatePickerDialog(this)
            }
        }

        radiusEditText = findViewById(R.id.radius_et)
        petSpeciesEditText = findViewById(R.id.petSpeciesEditText)
        petBreedEditText = findViewById(R.id.petBreedEditText)
        petColorEditText = findViewById(R.id.petColorEditText)
        petIllnessEditText = findViewById(R.id.petPreExistingIllnessMultiLineText)
        resetFilterButton = findViewById(R.id.resetFilter_btn)

        resetFilterButton.setOnClickListener {
            radiusEditText.setText("")
            petSpeciesEditText.setText("")
            petBreedEditText.setText("")
            petBirthdayButton.text = "Geburtstag"
            petColorEditText.setText("")
            petIllnessEditText.setText("")
        }

    }

    private fun showDatePickerDialog(v: View) {
        newFragment.show(supportFragmentManager, "datePicker")
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
}
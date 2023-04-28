package de.hhn.softwarelabor.pawswipeapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class EditAnimalActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var deletePetProfile: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_animal)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        spinner = findViewById(R.id.petGenderSpinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }

        cancelButton = findViewById(R.id.cancel_btn)
        cancelButton.setOnClickListener {
            val intent = Intent(this@EditAnimalActivity, ChatActivity::class.java)
            startActivity(intent)
        }

        saveButton = findViewById(R.id.save_btn)
        saveButton.setOnClickListener {

            AlertDialog.Builder(this@EditAnimalActivity)
                .setTitle(getString(R.string.saveChanges_headerText))
                .setMessage(getString(R.string.saveChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->


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
    }

    private fun changeName() {}

    private fun changeSpecies() {}

    private fun changeBreed() {}

    private fun changeGender() {}

    private fun changeBirthday() {}

    private fun changeColor() {}

    private fun changeIllness() {}

    private fun deletePetProfile() {}
}
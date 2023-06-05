package de.hhn.softwarelabor.pawswipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import de.hhn.softwarelabor.pawswipeapp.api.animal.AnimalProfileApi

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val newIllness : EditText = findViewById(R.id.newIllnessesEditText)
        val newDescription : EditText = findViewById(R.id.newDescriptionEditText)
        val newColor : EditText = findViewById(R.id.newColorEditText)
        val cancel : Button = findViewById(R.id.cancelButton)
        val confirmChanges : Button = findViewById(R.id.confirmChangesButton)

        cancel.setOnClickListener {

        }
        confirmChanges.setOnClickListener {
            val changedIllness : String? = newIllness.text.toString().takeIf { it.isNotBlank() }
            val changedColor : String? = newColor.text.toString().takeIf { it.isNotBlank() }
            val changedDescription : String? = newDescription.text.toString().takeIf { it.isNotBlank() }
            val animalProfileApi = AnimalProfileApi()
             //animalProfileApi.updateAnimalProfileByID(1)
        }


    }




}
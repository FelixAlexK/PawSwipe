package de.hhn.softwarelabor.pawswipeapp

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_animal)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        spinner = findViewById(R.id.petGenderSpinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }

        cancelButton = findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            val intent = Intent(this@EditAnimalActivity, ChatActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changeName() {}

    private fun changeSpecies() {}

    private fun changeBreed() {}

    private fun changeGender() {}

    private fun changeBirthday() {}

    private fun changeColor() {}

    private fun changeIllness() {}

}
package de.hhn.softwarelabor.pawswipeapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button

class EditShelterActivity : AppCompatActivity() {

    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var deleteShelterProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_shelter)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        cancelButton = findViewById(R.id.cancelBtn)
        cancelButton.setOnClickListener {
            AlertDialog.Builder(this@EditShelterActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent = Intent(this@EditShelterActivity, ChatActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        saveButton = findViewById(R.id.saveBtn)
        saveButton.setOnClickListener {

            AlertDialog.Builder(this@EditShelterActivity)
                .setTitle(getString(R.string.saveChanges_headerText))
                .setMessage(getString(R.string.saveChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent = Intent(this@EditShelterActivity, ChatActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        deleteShelterProfile = findViewById(R.id.deleteShelterProfile)
        deleteShelterProfile.setOnClickListener {

            AlertDialog.Builder(this@EditShelterActivity)
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
}
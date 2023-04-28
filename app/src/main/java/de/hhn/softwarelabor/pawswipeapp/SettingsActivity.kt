package de.hhn.softwarelabor.pawswipeapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import de.hhn.softwarelabor.pawswipeapp.api.UserProfileApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SettingsActivity : AppCompatActivity() {

    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var deleteUserProfile: Button
    private var userProfileApi: UserProfileApi = UserProfileApi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        cancelButton = findViewById(R.id.cancel_btn)
        cancelButton.setOnClickListener {
            val intent = Intent(this@SettingsActivity, ChatActivity::class.java)
            startActivity(intent)
        }

        saveButton = findViewById(R.id.saveChangesButton)
        saveButton.setOnClickListener {

            AlertDialog.Builder(this@SettingsActivity)
                .setTitle(getString(R.string.saveChanges_headerText))
                .setMessage(getString(R.string.saveChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->


                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        deleteUserProfile = findViewById(R.id.deleteUserProfile)
        deleteUserProfile.setOnClickListener {

            AlertDialog.Builder(this@SettingsActivity)
                .setTitle(getString(R.string.deleteProfile_headerText))
                .setMessage(getString(R.string.deleteProfile_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->

                    deleteUserProfile(41)

                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }

    private fun changeName() {}

    private fun changeFirstName() {}

    private fun changeEmail() {}

    private fun changeAddress() {}

    private fun changeBirthday() {}

    private fun deleteUserProfile(id: Int) {
        try {

            userProfileApi.deleteUserProfileByID(id){ response, error ->
                run {
                    if (error != null) {
                        runOnUiThread {
                            Toast.makeText(this@SettingsActivity,
                                "Ein Fehler ist aufgetretten (code: $response)",
                                Toast.LENGTH_SHORT).show()
                        }
                    }else if(response != null){
                        runOnUiThread { Toast.makeText(this@SettingsActivity,
                            "Profil wurde unwiderrufbar gelöscht!",
                            Toast.LENGTH_SHORT).show() }

                    }
                }

            }
        }catch (e: Exception){
            e.printStackTrace()
        }

    }
}
package de.hhn.softwarelabor.pawswipeapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import java.text.SimpleDateFormat
import java.util.*


class SettingsActivity : AppCompatActivity() {

    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var deleteUserProfile: Button
    private lateinit var userBirthdayButton: Button

    private var newFragment: DatePickerFragment = DatePickerFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        cancelButton = findViewById(R.id.cancel_btn)
        cancelButton.setOnClickListener {
            AlertDialog.Builder(this@SettingsActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent = Intent(this@SettingsActivity, ChatActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        saveButton = findViewById(R.id.save_btn)
        saveButton.setOnClickListener {

            AlertDialog.Builder(this@SettingsActivity)
                .setTitle(getString(R.string.saveChanges_headerText))
                .setMessage(getString(R.string.saveChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    val intent = Intent(this@SettingsActivity, ChatActivity::class.java)
                    startActivity(intent)
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


                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        userBirthdayButton = findViewById(R.id.userBirthdayButton)
        userBirthdayButton.text = getCurrentDate()

        newFragment.setOnDatePickedListener { date ->
            userBirthdayButton.text = date
        }
        userBirthdayButton.apply {

            setOnClickListener {
                showDatePickerDialog(this)
            }
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
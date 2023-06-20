package de.hhn.softwarelabor.pawswipeapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.animal.AnimalProfileApi
import de.hhn.softwarelabor.pawswipeapp.utils.Base64Utils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ShowAnimalProfile : AppCompatActivity() {
    private lateinit var pictureView: ImageView
    private lateinit var descriptionView: TextView
    private lateinit var nameView: TextView
    private lateinit var ageView: TextView
    private var animalProfileApi: AnimalProfileApi = AnimalProfileApi()
    private var animalId: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_animal_profile)

        //animalId = intent.getIntExtra("animal_id", 0)

        init()

        animalId = 22
        var displayedName: String?
        var displayedDescription: String?
        var displayedAge: Int
        var birthdate: Date?
        var age: Int?


        animalProfileApi.getAnimalProfileByID(animalId) { response, error ->
            if (error != null) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                runOnUiThread {
                    displayedName = response?.name
                    displayedDescription = response?.description

                    val picture = response?.picture_one?.let { Base64Utils.decode(it) }
                    val dateString = response?.birthday
                    val dateFormat = SimpleDateFormat(
                        resources.getString(R.string.en_dateFormat),
                        Locale.getDefault()
                    )
                    val birthdayDate = dateString?.let { dateFormat.parse(it) }
                    age = birthdayDate?.let { calculateAge(it) }

                    nameView.text = "Name: $displayedName"
                    descriptionView.text = displayedDescription
                    ageView.text = "Alter: " + age.toString()

                    pictureView.setImageBitmap(picture)
                }
            }


        }

    }

    private fun init() {

        ageView = findViewById(R.id.ageTF)
        descriptionView = findViewById(R.id.descriptionTF)
        nameView = findViewById(R.id.nameTF)
        pictureView = findViewById(R.id.pictureFinProfile)
    }

    private fun calculateAge(birthday: Date): Int {
        val today = Calendar.getInstance()
        val birthDate = Calendar.getInstance().apply { time = birthday }

        var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)

        if (today.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) ||
            (today.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birthDate.get(
                Calendar.DAY_OF_MONTH
            ))
        ) {
            age--
        }

        return age
    }
}
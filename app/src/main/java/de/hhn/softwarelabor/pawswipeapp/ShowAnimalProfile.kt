package de.hhn.softwarelabor.pawswipeapp

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import de.hhn.softwarelabor.pawswipeapp.api.animal.AnimalProfileApi
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
import java.text.SimpleDateFormat
import java.util.*
import com.squareup.picasso.Picasso

class ShowAnimalProfile : AppCompatActivity() {
    lateinit var pictureView: ImageView
    lateinit var descriptionView : TextView
    lateinit var nameView : TextView
    lateinit var ageView : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_animal_profile)
        init()

        var displayedName: String?
        var displayedDescription: String?
        var displayedAge : Int
        var birthdate : Date
        var age : Int?




        val id: Int = 1
        val api = AnimalProfileApi()
        api.getAnimalProfileByID(id) { response, error ->
            if (error != null) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                runOnUiThread {
                    displayedName = response?.get("name")?.asString
                    displayedDescription = response?.get("description")?.asString
                    val picture = response?.getAsJsonPrimitive("picture_one")?.asString
                    val dateString  = response?.getAsJsonPrimitive("birthday")?.asString
                    birthdate = SimpleDateFormat("yyyy-MM-dd").parse(dateString)
                    age = calculateAge(birthdate)

                    ageView.text = "Alter: " + age.toString()
                    nameView.text = "Name: $displayedName"
                    descriptionView.text = displayedDescription

                    Picasso.get().load(picture).into(pictureView)
                    //pictureView.setImageDrawable(finishedPic)
                }
            }


        }


    }
    private fun init(){

        ageView = findViewById(R.id.ageTF)
        descriptionView = findViewById(R.id.descriptionTF)
        nameView = findViewById(R.id.nameTF)
        pictureView = findViewById(R.id.pictureFinProfile)
    }
    private fun calculateAge(birthDate: Date): Int {
        val currentDate = Calendar.getInstance().time

        val birthCalendar = Calendar.getInstance()
        birthCalendar.time = birthDate

        var age = Calendar.getInstance().get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

        if (birthCalendar.get(Calendar.MONTH) > Calendar.getInstance().get(Calendar.MONTH) ||
            (birthCalendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
                    birthCalendar.get(Calendar.DAY_OF_MONTH) > Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        ) {
            age--
        }

        return age
    }
}
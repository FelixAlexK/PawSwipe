package de.hhn.softwarelabor.pawswipeapp


import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import de.hhn.softwarelabor.pawswipeapp.api.animal.AnimalProfileApi
import de.hhn.softwarelabor.pawswipeapp.api.user.ProfileApi
import de.hhn.softwarelabor.pawswipeapp.utils.Base64Utils
import de.hhn.softwarelabor.pawswipeapp.utils.DatePickerFragment

private const val MULTILINE_TEXT_LENGTH = 255

/**
 * The PetProfileActivity class handles the user interface for creating and managing pet profiles.
 * It provides functionality to input pet details, upload images, and save or cancel changes.
 * @author Felix Kuhbier
 */
class PetProfileActivity : AppCompatActivity() {


    private lateinit var genderSpinner: Spinner
    private lateinit var speciesSpinner: Spinner
    private lateinit var breedSpinner: Spinner

    private lateinit var uploadPictureButton: Button
    private lateinit var cancelPetButton: Button
    private lateinit var createPetButton: Button

    private lateinit var petNameEditText: EditText
    private lateinit var petBirthdayButton: Button
    private lateinit var petColorEditText: EditText
    private lateinit var petIllnessMultilineText: EditText
    private lateinit var petDescriptionText: EditText
    private lateinit var petProfileImageView: ImageView

    private lateinit var datePickerFragment: DatePickerFragment

    private var animalProfile: AnimalProfileApi = AnimalProfileApi()
    private var id: Int = 0

    private var pictureOne: String? = null
    private var pictureTwo: String? = null
    private var pictureThree: String? = null
    private var pictureFour: String? = null
    private var pictureFive: String? = null
    private var birthday: String? = null


    private fun checkEmpty(string: String) : Boolean {
        val newString = string.replace(" ", "")
        if (newString == "")
            return true
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_profil)
        init()
        id = intent.getIntExtra("id", 0)

        datePickerFragment =
            DatePickerFragment(this.getString(R.string.de_dateFormat), this@PetProfileActivity)

        createPetButton.setOnClickListener {
            if(
                // TODO Check for ProfilePic, after implemented the upload function
                speciesSpinner.selectedItem == speciesSpinner.getItemAtPosition(0)
                || breedSpinner.selectedItem.toString() == breedSpinner.getItemAtPosition(0)
                || genderSpinner.selectedItem.toString() == genderSpinner.getItemAtPosition(0)
                || petBirthdayButton.text.toString() == resources.getString(R.string.birthday_text)
                || checkEmpty(petIllnessMultilineText.text.toString())
            ) {
                Toast.makeText(
                    this@PetProfileActivity,
                    "Bitte alle Pflichtfelder ausfüllen.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (petDescriptionText.length() <= MULTILINE_TEXT_LENGTH) {
        
                    val birthday =
                        datePickerFragment.convertDateToServerCompatibleDate(petBirthdayButton.text.toString())
                    createPet(
                        petNameEditText.text.toString(),
                        speciesSpinner.selectedItem.toString(),
                        birthday,
                        petIllnessMultilineText.text.toString(),
                        petDescriptionText.text.toString(),
                        breedSpinner.selectedItem.toString(),
                        petColorEditText.text.toString(),
                        genderSpinner.selectedItem.toString(),
                        pictureOne,
                    pictureTwo,
                    pictureThree,
                    pictureFour,
                    pictureFive,
                        id
                    )
        
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "Beschreibung ist zu lang: ${petDescriptionText.length()}/${MULTILINE_TEXT_LENGTH}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                
            }
            
 
        }

        cancelPetButton.setOnClickListener {
            AlertDialog.Builder(this@PetProfileActivity)
                .setTitle(getString(R.string.cancelChanges_headerText))
                .setMessage(getString(R.string.cancelChanges_messageText))
                .setPositiveButton(getString(R.string.yes_dialogText)) { dialog, _ ->
                    petNameEditText.setText("")
                    petIllnessMultilineText.setText("")
                    petDescriptionText.setText("")
                    petColorEditText.setText("")
                    val intent = Intent(this@PetProfileActivity, MatchActivity::class.java)
                    intent.putExtra("id", id)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no_dialogText)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        datePickerFragment.setOnDatePickedListener { date ->
            petBirthdayButton.text = date
        }


        petBirthdayButton.apply {

            setOnClickListener {
                showDatePickerDialog(this)
            }
        }

        uploadPictureButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            pickImageLauncher.launch(intent)
        }

    }

    /**
     * The pickImageLauncher is used to launch an intent for selecting images from the device's gallery.
     * It handles the result of the image selection and stores the selected images as Base64 encoded strings.
     * It also sets the first selected image as the pet's profile picture.
     */
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImages = mutableListOf<Uri>()
            val clipData = result.data?.clipData

            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    selectedImages.add(uri)
                }
            }
            petProfileImageView.setImageURI(selectedImages[0])
            pictureOne =
                if (selectedImages.size > 0) Base64Utils.encode(getBitmapFromUri(selectedImages[0])) else null
            pictureTwo =
                if (selectedImages.size > 1) Base64Utils.encode(getBitmapFromUri(selectedImages[1])) else null
            pictureThree =
                if (selectedImages.size > 2) Base64Utils.encode(getBitmapFromUri(selectedImages[2])) else null
            pictureFour =
                if (selectedImages.size > 3) Base64Utils.encode(getBitmapFromUri(selectedImages[3])) else null
            pictureFive =
                if (selectedImages.size > 4) Base64Utils.encode(getBitmapFromUri(selectedImages[4])) else null
        }
    }

    /**
     * Creates a new pet profile with the provided details.
     *
     * @param name Pet's name.
     * @param species Pet's species.
     * @param birthday Pet's birthday.
     * @param illness Pet's pre-existing illness.
     * @param description Pet's description.
     * @param breed Pet's breed.
     * @param color Pet's color.
     * @param gender Pet's gender.
     * @param picture_one Base64 encoded string of the first pet image.
     * @param picture_two Base64 encoded string of the second pet image.
     * @param picture_three Base64 encoded string of the third pet image.
     * @param picture_four Base64 encoded string of the fourth pet image.
     * @param picture_five Base64 encoded string of the fifth pet image.
     * @param profile_id User's profile ID.
     */
    private fun createPet(
        name: String?,
        species: String?,
        birthday: String?,
        illness: String?,
        description: String?,
        breed: String?,
        color: String?,
        gender: String?,
        picture_one: String?,
        picture_two: String?,
        picture_three: String?,
        picture_four: String?,
        picture_five: String?,
        profile_id: Int
    ) {

        val profile = ProfileApi()



        profile.getUserProfileByID(profile_id) { user, error ->


            if (error != null) {
                Log.e(TAG, error.message.toString())
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (user != null) {
                animalProfile.createAnimalProfile(
                    name,
                    species,
                    birthday,
                    illness,
                    description,
                    breed,
                    color,
                    gender,
                    picture_one,
                    picture_two,
                    picture_three,
                    picture_four,
                    picture_five,
                    user
                ) { profile, error ->

                    if (error != null) {
                        Log.e(TAG, error.message.toString())
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else if (profile != null) {
                        Log.d(TAG, "Successful: $profile")
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "Profil für $name wurde erfolgreich erstellt!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val intent = Intent(this@PetProfileActivity, MatchActivity::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                    }
                }
            }
        }


    }


    /**
     * Shows the date picker dialog for selecting the pet's birthday.
     *
     * @param v The view that triggered this method.
     */
    private fun showDatePickerDialog(v: View) {
        datePickerFragment.show(supportFragmentManager, "datePicker")

    }


    /**
     * Initializes UI components and sets up event listeners.
     */
    private fun init() {
        try {

            uploadPictureButton = findViewById(R.id.addPetProfileImageButton)
            cancelPetButton = findViewById(R.id.cancel_btn)
            createPetButton = findViewById(R.id.save_btn)

            petBirthdayButton = findViewById(R.id.petBirthdayButton)
            petNameEditText = findViewById(R.id.petNameEditText)
            petColorEditText = findViewById(R.id.petColorEditText)
            petDescriptionText = findViewById(R.id.petDescriptionMultiLineText)

            petIllnessMultilineText = findViewById(R.id.petPreExistingIllnessMultiLineText)

            petProfileImageView = findViewById(R.id.petProfileImageView)


            genderSpinner = findViewById(R.id.petGenderSpinner)

            ArrayAdapter.createFromResource(
                this, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                genderSpinner.adapter = adapter
            }

            speciesSpinner = findViewById(R.id.petProfileSpeciesSpinner)
            ArrayAdapter.createFromResource(
                this,
                R.array.species_array,
                android.R.layout.simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                speciesSpinner.adapter = adapter
            }

            speciesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    breedSpinner = findViewById(R.id.petProfileBreedsSpinner)
                    when (parent.getItemAtPosition(position).toString()) {
                        "Hund" -> {
                            ArrayAdapter.createFromResource(
                                breedSpinner.context,
                                R.array.dog_array,
                                android.R.layout.simple_spinner_dropdown_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                breedSpinner.adapter = adapter
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

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message.toString())
            e.printStackTrace()
        }

    }

    /**
     * Converts the given Uri into a Bitmap.
     *
     * @param uri The Uri of the image to convert.
     * @return The Bitmap representation of the image.
     */
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        return bitmap
    }


}
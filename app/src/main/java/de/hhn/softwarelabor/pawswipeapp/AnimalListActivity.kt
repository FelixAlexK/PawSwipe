package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hhn.softwarelabor.pawswipeapp.api.animal.AnimalProfileApi
import de.hhn.softwarelabor.pawswipeapp.api.like.LikeApi
import de.hhn.softwarelabor.pawswipeapp.utils.AnimalAdapter
import de.hhn.softwarelabor.pawswipeapp.utils.AnimalItem
import de.hhn.softwarelabor.pawswipeapp.utils.Base64Utils
import de.hhn.softwarelabor.pawswipeapp.utils.BitmapScaler


/**
 * AnimalListActivity is an AppCompatActivity that displays a list of liked animals.
 * @author Felix Kuhbier & Leo Kalmbach
 *
 * @property matchBtn Button to navigate to the MatchActivity.
 * @property chatBtn Button to navigate to the ChatActivity.
 * @property recyclerView RecyclerView that displays the list of liked animals.
 * @property animalAdapter AnimalAdapter for managing the list of liked animals in the RecyclerView.
 * @property animalItems ArrayList of AnimalItem objects representing the liked animals.
 * @property profileId Integer representing the profile ID of the current user.
 */

private const val BITMAP_WIDTH = 250
private const val BITMAP_HEIGHT = 250

class AnimalListActivity : AppCompatActivity() {

    private lateinit var matchBtn: Button
    private lateinit var chatBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var animalAdapter: AnimalAdapter
    private var animalItems: ArrayList<AnimalItem> = ArrayList()
    private var profileId: Int = 0

    /**
     * Initializes the activity, sets up the UI and loads the liked animals.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_list)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        profileId = intent.getIntExtra("id", 0)
        getAnimalItemsFromAnimalIds(profileId)
        recyclerView = findViewById(R.id.animal_list_recyclerView)
        animalAdapter = AnimalAdapter(animalItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = animalAdapter
        matchBtn = findViewById(R.id.matching_btn3)
        chatBtn = findViewById(R.id.chat_btn3)


        matchBtn.setOnClickListener {
            val intent = Intent(this@AnimalListActivity, MatchActivity::class.java)
            intent.putExtra("id", profileId)
            startActivity(intent)
        }

        chatBtn.setOnClickListener {
            val intent = Intent(this@AnimalListActivity, ChatActivity::class.java)
            startActivity(intent)
        }

        updateEmptyTextViewVisibility()
    }

    /**
     * Inflates the options menu.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handles the selected option from the options menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intent = Intent(this@AnimalListActivity, SettingsActivity::class.java)
                intent.putExtra("id", profileId)
                startActivity(intent)
                true
            }

            R.id.menu_animalServices -> {
                val intent = Intent(this@AnimalListActivity, AnimalServiceActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_animalEdit -> {
                val intent = Intent(this@AnimalListActivity, EditAnimalActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_animalCreate -> {
                val intent = Intent(this@AnimalListActivity, PetProfileActivity::class.java)
                intent.putExtra("id", profileId)
                startActivity(intent)
                true
            }

            R.id.menu_logOut -> {
                val intent = Intent(this@AnimalListActivity, LoginActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_filter -> {
                val intent = Intent(this@AnimalListActivity, FilterActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Updates the visibility of the emptyTextView based on the size of the animalItems ArrayList.
     */
    private fun updateEmptyTextViewVisibility() {
        val emptyTextView: TextView = findViewById(R.id.animal_list_empty_textView)
        emptyTextView.visibility = if (animalItems.isEmpty()) View.VISIBLE else View.GONE
    }

    /**
     * Retrieves AnimalItem objects based on the liked animal IDs for the given profileId.
     *
     * @param profileId Integer representing the profile ID of the current user.
     */
    private fun getAnimalItemsFromAnimalIds(profileId: Int) {
        val likeApi = LikeApi()
        val animalProfileApi = AnimalProfileApi()

        //get all the IDs of animals you like
        likeApi.getLikedAnimals(profileId) { response, error ->
            if (error != null) {
                runOnUiThread {
                    Toast.makeText(
                        this@AnimalListActivity,
                        "Tiere konnten nicht geladen werden",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                response?.forEach { id ->

                    //Get profiles from IDs and add them to the list
                    animalProfileApi.getAnimalProfileByID(id) { response, error ->

                        runOnUiThread {
                            if (error != null) {

                                Toast.makeText(
                                    this@AnimalListActivity,
                                    "${error.message}",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {


                                val item = AnimalItem(
                                    response?.picture_one?.let {
                                        BitmapScaler.scaleToFitWidth(
                                            Base64Utils.decode(it),
                                            BITMAP_WIDTH
                                        )

                                        BitmapScaler.scaleToFitHeight(
                                            Base64Utils.decode(it),
                                            BITMAP_HEIGHT
                                        )
                                    },
                                    response?.name,
                                    response?.species,
                                    response?.breed
                                )
                                animalAdapter.addItem(item)
                                updateEmptyTextViewVisibility()

                            }
                        }
                    }

                }
            }
        }

    }
}




package de.hhn.softwarelabor.pawswipeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

class MatchActivity : AppCompatActivity() {

    private lateinit var chatBtn: Button
    private lateinit var animalListBtn: Button
    private lateinit var likeBtn: ImageButton
    private lateinit var dislikeBtn: ImageButton
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var imageList: List<Int>
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        id = intent.getIntExtra("id", 0)

        chatBtn = findViewById(R.id.chat_btn2)
        chatBtn.setOnClickListener {
            val intent = Intent(this@MatchActivity, ChatActivity::class.java)
            startActivity(intent)
        }

        animalListBtn = findViewById(R.id.animalList_btn2)
        animalListBtn.setOnClickListener {
            val intent = Intent(this@MatchActivity, AnimalListActivityNico::class.java)
            startActivity(intent)
        }

        likeBtn = findViewById(R.id.like_btn)
        likeBtn.setOnClickListener {
            likeBtn.setImageResource(R.drawable.heart_liked_small)
        }

        dislikeBtn = findViewById(R.id.dislike_btn)
        dislikeBtn.setOnClickListener {

        }

        viewPager = findViewById(R.id.idViewPager)


        imageList = ArrayList()
        imageList = imageList + R.drawable.pixabay_cute_cat
        imageList = imageList + R.drawable.heart_liked_small
        imageList = imageList + R.drawable.dislike_x

        viewPagerAdapter = ViewPagerAdapter(this@MatchActivity, imageList)

        viewPager.adapter = viewPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intent = Intent(this@MatchActivity, SettingsActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                true
            }
            R.id.menu_animalServices -> {
                val intent = Intent(this@MatchActivity, AnimalServiceActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalEdit -> {
                val intent = Intent(this@MatchActivity, EditAnimalActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_animalCreate -> {
                val intent = Intent(this@MatchActivity, PetProfileActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                true
            }
            R.id.menu_logOut -> {
                val intent = Intent(this@MatchActivity, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_filter -> {
                val intent = Intent(this@MatchActivity, FilterActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
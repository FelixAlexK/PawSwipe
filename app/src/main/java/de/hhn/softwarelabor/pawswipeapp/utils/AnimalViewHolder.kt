package de.hhn.softwarelabor.pawswipeapp.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hhn.softwarelabor.pawswipeapp.R

class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.animal_item_imageView)
    private val nameTextView: TextView = itemView.findViewById(R.id.animal_item_name_textView)
    private val breedTextView: TextView = itemView.findViewById(R.id.animal_item_breed_textView)
    private val speciesTextView: TextView = itemView.findViewById(R.id.animal_item_species_textView)

    fun bind(item: AnimalItem) {
        item.imageResId?.let { imageView.setImageResource(it) }
        nameTextView.text = item.animalName
        breedTextView.text = item.animalBreed
        speciesTextView.text = item.animalSpecies
    }
}
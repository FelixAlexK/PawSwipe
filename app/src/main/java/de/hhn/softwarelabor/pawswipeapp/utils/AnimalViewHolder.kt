package de.hhn.softwarelabor.pawswipeapp.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hhn.softwarelabor.pawswipeapp.R

/**
 * AnimalViewHolder is a custom RecyclerView.ViewHolder for displaying animal items.
 * It holds references to the necessary views in the animal item layout and populates
 * them with data from an AnimalItem object.
 *
 * @property itemView The View representing an individual animal item in the RecyclerView.
 *
 * @author Felix Kuhbier
 */
class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.animal_item_imageView)
    private val nameTextView: TextView = itemView.findViewById(R.id.animal_item_name_textView)
    private val breedTextView: TextView = itemView.findViewById(R.id.animal_item_breed_textView)
    private val speciesTextView: TextView = itemView.findViewById(R.id.animal_item_species_textView)

    /**
     * Binds the data from the given AnimalItem object to the views in the ViewHolder.
     *
     * @param item The AnimalItem object containing the data to be displayed.
     */
    fun bind(item: AnimalItem) {
        item.imageResId?.let { imageView.setImageBitmap(it) }
        nameTextView.text = item.animalName
        breedTextView.text = item.animalBreed
        speciesTextView.text = item.animalSpecies
    }
}
package de.hhn.softwarelabor.pawswipeapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.hhn.softwarelabor.pawswipeapp.R

/**
 * AnimalAdapter is a custom RecyclerView.Adapter for displaying a list of animal items.
 * It takes an ArrayList of AnimalItem objects and binds the data to the AnimalViewHolder.
 *
 * @property animalItems The list of animal items to be displayed in the RecyclerView.
 *
 * @author Felix Kuhbier
 */
class AnimalAdapter(private val animalItems: ArrayList<AnimalItem>) :
    RecyclerView.Adapter<AnimalViewHolder>() {

    /**
     * Called when the RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.animal_item, parent, false)
        return AnimalViewHolder(view)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the list of animal items.
     */
    override fun getItemCount(): Int = animalItems.size

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder's itemView to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(animalItems[position])
    }

    /**
     * Adds an item to the list of animal items and notifies the adapter that the item has been inserted.
     *
     * @param item The AnimalItem object to be added to the list.
     */
    fun addItem(item: AnimalItem) {
        animalItems.add(item)
        notifyItemInserted(animalItems.size - 1)
    }

}
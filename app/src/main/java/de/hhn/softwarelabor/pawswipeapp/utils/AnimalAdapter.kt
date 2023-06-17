package de.hhn.softwarelabor.pawswipeapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.hhn.softwarelabor.pawswipeapp.R

class AnimalAdapter(private val animalItems: ArrayList<AnimalItem>) :
    RecyclerView.Adapter<AnimalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.animal_item, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount(): Int = animalItems.size


    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(animalItems[position])
    }

    fun addItem(item: AnimalItem) {
        animalItems.add(item)
        notifyItemInserted(animalItems.size - 1)
    }

}
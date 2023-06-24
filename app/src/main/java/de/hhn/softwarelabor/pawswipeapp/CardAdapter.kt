package de.hhn.softwarelabor.pawswipeapp
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hhn.softwarelabor.pawswipeapp.api.data.AnimalProfileData
import de.hhn.softwarelabor.pawswipeapp.utils.Base64Utils
import de.hhn.softwarelabor.pawswipeapp.utils.BitmapScaler

class CardAdapter(private val context: Context, private val animals: List<AnimalProfileData>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    
    fun getAnimal(position: Int): AnimalProfileData {
        return animals[position]
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = animals[position]
        holder.bind(item)
    }
    
    override fun getItemCount(): Int {
        return animals.size
    }
    
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val picture: ImageView = itemView.findViewById(R.id.picture_card_IV)
        private val name: TextView = itemView.findViewById(R.id.tiername_item_TV)
        private val species: TextView = itemView.findViewById(R.id.species_item_TV)
        private val breed: TextView = itemView.findViewById(R.id.breed_item_TV)
        private val gender: TextView = itemView.findViewById(R.id.gender_item_TV)
        private val birthday: TextView = itemView.findViewById(R.id.birthday_item_TV)
        
        fun bind(animal: AnimalProfileData) {
            
            val screenWidth = context.resources.displayMetrics.widthPixels
            
            // Überprüfe, ob das Bild vorhanden ist
            
            animal.picture_one?.let { pictureOne ->
                // Dekodiere das Bild aus dem Base64-String
                val decodedBitmap = Base64Utils.decode(pictureOne)
        
                // Skaliere das Bild entsprechend der gewünschten Breite und Höhe
                val scaledBitmap = BitmapScaler.scaleToFitWidth(decodedBitmap, screenWidth)
                // Oder:
                // val scaledBitmap = BitmapScaler.scaleToFitHeight(decodedBitmap, screenHeight)
        
                // Setze das skalierte Bild in das ImageView
                picture.setImageBitmap(scaledBitmap)
                name.text = animal.name
                species.text = animal.species
                breed.text = animal.breed
                gender.text = animal.gender
                birthday.text = animal.birthday
            }
        }
    }
}

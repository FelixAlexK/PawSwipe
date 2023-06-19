package de.hhn.softwarelabor.pawswipeapp.utils

import android.graphics.Bitmap

data class AnimalItem(
    val imageResId: Bitmap?,
    val animalName: String?,
    val animalSpecies: String?,
    val animalBreed: String?
)

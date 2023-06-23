package de.hhn.softwarelabor.pawswipeapp.utils

import android.graphics.Bitmap

/**
 * Animal item
 *
 * @property imageResId
 * @property animalName
 * @property animalSpecies
 * @property animalBreed
 * @constructor Create empty Animal item
 */
data class AnimalItem(
    val animalID: Int?,
    val imageResId: Bitmap?,
    val animalName: String?,
    val animalSpecies: String?,
    val animalBreed: String?
)

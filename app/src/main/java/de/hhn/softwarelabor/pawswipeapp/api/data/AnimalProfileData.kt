package de.hhn.softwarelabor.pawswipeapp.api.data

import de.hhn.softwarelabor.pawswipeapp.api.UserProfileApi

/**
 * [AnimalProfile], holds data for an animal profile
 *
 * @property name
 * @property species
 * @property birthday
 * @property illness
 * @property description
 * @property breed
 * @property color
 * @property gender
 * @property profile
 */
data class AnimalProfileData(
    val name: String?,
    val species: String?,
    val birthday: String?,
    val illness: String?,
    val description: String?,
    val breed: String?,
    val color: String?,
    val gender: String?,
    val profile: ShelterProfileData
)

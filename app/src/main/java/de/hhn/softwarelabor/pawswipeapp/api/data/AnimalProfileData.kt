package de.hhn.softwarelabor.pawswipeapp.api.data

/**
 * [AnimalProfileData], holds data for an animal profile
 * @author Felix Kuhbier
 * @since 2023.05.06
 *
 * @property name
 * @property species
 * @property birthday
 * @property illness
 * @property description
 * @property breed
 * @property color
 * @property gender
 * @property profile_id must not be null
 */
data class AnimalProfileData(
    val animal_id: Int?,
    val name: String?,
    val species: String?,
    val birthday: String?,
    val illness: String?,
    val description: String?,
    val breed: String?,
    val color: String?,
    val gender: String?,
    val picture_one: String?,
    val picture_two: String?,
    val picture_three: String?,
    val picture_four: String?,
    val picture_five: String?,
    val profile_id: ProfileData
)

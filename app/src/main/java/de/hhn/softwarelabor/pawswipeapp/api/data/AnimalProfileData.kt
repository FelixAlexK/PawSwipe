package de.hhn.softwarelabor.pawswipeapp.api.data

/**
 * [AnimalProfile], holds data for an animal profile
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
    val name: String?,
    val species: String?,
    val birthday: String?,
    val illness: String?,
    val description: String?,
    val breed: String?,
    val color: String?,
    val gender: String?,
    val profile_id: ProfileData
)

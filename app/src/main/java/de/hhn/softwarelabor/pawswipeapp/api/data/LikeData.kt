package de.hhn.softwarelabor.pawswipeapp.api.data

/**
 * LikeData is a data class representing the relationship between a profile and an animal that has been liked.
 *
 * @author Felix Kuhbier
 * @since 10.6.2023
 *
 * @property profile_id The ID of the profile.
 * @property animal_id The ID of the liked animal.
 * @constructor Creates a LikeData instance with the given profile ID and animal ID.
 */
data class LikeData(
    val profile_id: Int,
    val animal_id: Int
)

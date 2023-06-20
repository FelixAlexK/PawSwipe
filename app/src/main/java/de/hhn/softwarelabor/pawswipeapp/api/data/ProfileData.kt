package de.hhn.softwarelabor.pawswipeapp.api.data

/**
 * [ProfileData], holds data for an animal profile
 * @author Felix Kuhbier
 * @since 2023.05.06
 *
 * @property profile_id ID of the associated user profile, for creating an animal profile it must not be null
 * @property username May be null
 * @property email Must not be null
 * @property phone_number May be null
 * @property profile_picture May be null
 * @property description May be null
 * @property password Must not be null
 * @property creation_date May be null
 * @property birthday May be null
 * @property opening_hours May be null
 * @property street May be null
 * @property country May be null
 * @property city May be null
 * @property street_number May be null
 * @property homepage May be null
 * @property postal_code May be null
 * @property firstname May be null
 * @property lastname May be null
 * @property discriminator "profile" for an user profile or "shelter" for an shelter profile, must not be null
 */
data class ProfileData(
    val profile_id: Int? = null,
    val username: String,
    val email: String,
    val phone_number: String?,
    val profile_picture: String?,
    val description: String?,
    val password: String,
    val creation_date: String?,
    val birthday: String?,
    val opening_hours: String?,
    val street: String?,
    val country: String?,
    val city: String?,
    val street_number: String?,
    val homepage: String?,
    val postal_code: String?,
    val firstname: String?,
    val lastname: String?,
    val lat: Double?,
    val lon: Double?,
    val discriminator: String,
) 


package de.hhn.softwarelabor.pawswipeapp.api.data

import java.util.Date

/**
 * [ProfileData], holds data for an animal profile
 *
 * @property profileId ID of the associated user profile
 * @property username May be null
 * @property email Must not be null
 * @property phoneNumber May be null
 * @property profilePicture May be null
 * @property description May be null
 * @property password Must not be null
 * @property creationDate May be null
 * @property birthday May be null
 * @property openingHours May be null
 * @property street May be null
 * @property country May be null
 * @property city May be null
 * @property streetNumber May be null
 * @property homepage May be null
 * @property postalCode May be null
 * @property firstname May be null
 * @property lastname May be null
 * @property discriminator "profile" for an user profile or "shelter" for an shelter profile, must not be null
 */
data class ProfileData(
    val profile_id: Int?,
    val username: String?,
    val email: String,
    val phone_number: String?,
    val profile_picture: Array<Byte>?,
    val description: String?,
    val password: String,
    val creation_date: Date?,
    val birthday: String?,
    val opening_hours: String?,
    val street: String?,
    val country: String?,
    val city: String?,
    val street_number: Int?,
    val homepage: String?,
    val postal_code: String?,
    val firstname: String?,
    val lastname: String?,
    val discriminator: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileData

        if (profile_id != other.profile_id) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (phone_number != other.phone_number) return false
        if (profile_picture != null) {
            if (other.profile_picture == null) return false
            if (!profile_picture.contentEquals(other.profile_picture)) return false
        } else if (other.profile_picture != null) return false
        if (description != other.description) return false
        if (password != other.password) return false
        if (creation_date != other.creation_date) return false
        if (birthday != other.birthday) return false
        if (opening_hours != other.opening_hours) return false
        if (street != other.street) return false
        if (country != other.country) return false
        if (city != other.city) return false
        if (street_number != other.street_number) return false
        if (homepage != other.homepage) return false
        if (postal_code != other.postal_code) return false
        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false
        if (discriminator != other.discriminator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = profile_id.hashCode()
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + email.hashCode()
        result = 31 * result + (phone_number?.hashCode() ?: 0)
        result = 31 * result + (profile_picture?.contentHashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + password.hashCode()
        result = 31 * result + (creation_date?.hashCode() ?: 0)
        result = 31 * result + (birthday?.hashCode() ?: 0)
        result = 31 * result + (opening_hours?.hashCode() ?: 0)
        result = 31 * result + (street?.hashCode() ?: 0)
        result = 31 * result + (country?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (street_number ?: 0)
        result = 31 * result + (homepage?.hashCode() ?: 0)
        result = 31 * result + (postal_code?.hashCode() ?: 0)
        result = 31 * result + (firstname?.hashCode() ?: 0)
        result = 31 * result + (lastname?.hashCode() ?: 0)
        result = 31 * result + discriminator.hashCode()
        return result
    }
}

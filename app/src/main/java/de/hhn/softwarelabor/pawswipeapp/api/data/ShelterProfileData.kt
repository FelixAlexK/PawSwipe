package de.hhn.softwarelabor.pawswipeapp.api.data

import de.hhn.softwarelabor.pawswipeapp.api.UserProfileInterface
import java.util.Date

/**
 * [ShelterProfileData], holds data for an animal profile
 *
 * @property profileId ID of the profile
 * @property username name of the shelter
 * @property profilePicture picture of the shelter
 * @property description description of the shelter
 * @property password password of the profile
 * @property creationDate date on which the profile was created
 * @property email email of the shelter
 * @property birthday
 * @property phoneNumber phone-number of the shelter
 * @property openingHours opening hours of the shelter
 * @property street name of the street where the shelter is located
 * @property country country in which the shelter is located
 * @property city city in which the shelter is located
 * @property streetNumber number of street in which the shelter is located
 * @property homepage homepage of the shelter
 * @property postalCode postal code of the shelters location
 *
 */
data class ShelterProfileData(
    val profileId: Int,
    val username: String?,
    val email: String,
    val phoneNumber: String?,
    val profilePicture: Array<Byte>?,
    val description: String?,
    val password: String,
    val creationDate: Date?,
    val birthday: String?,
    val openingHours: String?,
    val street: String?,
    val country: String?,
    val city: String?,
    val streetNumber: Int?,
    val homepage: String?,
    val postalCode: String?,
    val firstname: String?,
    val lastname: String?,
    val discriminator: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShelterProfileData

        if (profileId != other.profileId) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (phoneNumber != other.phoneNumber) return false
        if (profilePicture != null) {
            if (other.profilePicture == null) return false
            if (!profilePicture.contentEquals(other.profilePicture)) return false
        } else if (other.profilePicture != null) return false
        if (description != other.description) return false
        if (password != other.password) return false
        if (creationDate != other.creationDate) return false
        if (birthday != other.birthday) return false
        if (openingHours != other.openingHours) return false
        if (street != other.street) return false
        if (country != other.country) return false
        if (city != other.city) return false
        if (streetNumber != other.streetNumber) return false
        if (homepage != other.homepage) return false
        if (postalCode != other.postalCode) return false
        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false
        if (discriminator != other.discriminator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = profileId
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + email.hashCode()
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + (profilePicture?.contentHashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + password.hashCode()
        result = 31 * result + (creationDate?.hashCode() ?: 0)
        result = 31 * result + (birthday?.hashCode() ?: 0)
        result = 31 * result + (openingHours?.hashCode() ?: 0)
        result = 31 * result + (street?.hashCode() ?: 0)
        result = 31 * result + (country?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (streetNumber ?: 0)
        result = 31 * result + (homepage?.hashCode() ?: 0)
        result = 31 * result + (postalCode?.hashCode() ?: 0)
        result = 31 * result + (firstname?.hashCode() ?: 0)
        result = 31 * result + (lastname?.hashCode() ?: 0)
        result = 31 * result + discriminator.hashCode()
        return result
    }

}

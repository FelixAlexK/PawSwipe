package de.hhn.softwarelabor.pawswipeapp.api.data

import java.util.Date

data class UserProfileData(
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

        other as UserProfileData

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
        var result = username?.hashCode() ?: 0
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

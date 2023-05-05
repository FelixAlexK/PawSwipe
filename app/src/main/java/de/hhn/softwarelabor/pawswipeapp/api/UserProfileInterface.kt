package de.hhn.softwarelabor.pawswipeapp.api

import de.hhn.softwarelabor.pawswipeapp.api.data.ShelterProfileData
import de.hhn.softwarelabor.pawswipeapp.api.data.UserProfileData
import java.util.Date

interface UserProfileInterface {


    fun createUserProfile(
        username: String?, profilePicture: Array<Byte>?, description: String?,
        password: String, creationDate: Date?, email: String,
        birthday: String?, phoneNumber: String?, openingHours: String?, street: String?,
        country: String?, city: String?, streetNumber: Int?, homepage: String?,
        postalCode: String?,firstname: String,lastname: String, discriminator: String, callback: (Int?, Throwable?) -> Unit
    )

    fun getAllUserProfileIDs(callback: (List<String>?, Throwable?) -> Unit)

    fun getUserProfileByID(
        id: Int,
        callback: (ShelterProfileData?, UserProfileData?, Throwable?) -> Unit
    )

    fun updateUserProfileByID(
        id: Int,
        map: Map<String, String>,
        callback: (Int?, Throwable?) -> Unit
    )

    fun deleteUserProfileByID(id: Int, callback: (Int?, Throwable?) -> Unit)

}
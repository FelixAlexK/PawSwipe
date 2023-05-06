package de.hhn.softwarelabor.pawswipeapp.api

import de.hhn.softwarelabor.pawswipeapp.api.data.ProfileData
import java.util.Date

interface ProfileInterface {


    fun createUserProfile(
        profile_id: Int?,
        username: String?,
        email: String,
        phone_number: String?,
        profile_picture: Array<Byte>?,
        description: String?,
        password: String,
        creation_date: Date?,
        birthday: String?,
        opening_hours: String?,
        street: String?,
        country: String?,
        city: String?,
        street_number: Int?,
        homepage: String?,
        postal_code: String?,
        firstname: String,
        lastname: String,
        discriminator: String,
        callback: (Int?, Throwable?) -> Unit
    )

    fun getAllUserProfileIDs(callback: (List<String>?, Throwable?) -> Unit)

    fun getUserProfileByID(
        id: Int,
        callback: (ProfileData?, Throwable?) -> Unit
    )

    fun getUserProfileByEmail(
        email: String,
        callback: (ProfileData?, Throwable?) -> Unit
    )

    fun updateUserProfileByID(
        id: Int,
        map: Map<String, String>,
        callback: (Int?, Throwable?) -> Unit
    )

    fun deleteUserProfileByID(id: Int, callback: (Int?, Throwable?) -> Unit)

}
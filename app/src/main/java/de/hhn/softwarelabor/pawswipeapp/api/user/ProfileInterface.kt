package de.hhn.softwarelabor.pawswipeapp.api.user

import de.hhn.softwarelabor.pawswipeapp.api.data.ProfileData
import java.lang.IllegalStateException
import java.util.Date

/**
 * Profile interface
 *@author Felix Kuhbier
 * @since 2023.05.06
 *
 */
interface ProfileInterface {


    /**
     * Create user profile
     *
     * @param profile_id
     * @param username
     * @param email
     * @param phone_number
     * @param profile_picture
     * @param description
     * @param password
     * @param creation_date
     * @param birthday
     * @param opening_hours
     * @param street
     * @param country
     * @param city
     * @param street_number
     * @param homepage
     * @param postal_code
     * @param firstname
     * @param lastname
     * @param discriminator
     * @param callback
     * @receiver
     */
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

    /**
     * Get all user profile i ds
     *
     * @param callback
     * @receiver
     */
    fun getAllUserProfileIDs(callback: (List<String>?, Throwable?) -> Unit)

    /**
     * Get user profile by i d
     *
     * @param id
     * @param callback
     * @receiver
     */
    fun getUserProfileByID(
        id: Int,
        callback: (ProfileData?, Throwable?) -> Unit
    )

    /**
     * Get user profile by email
     *
     * @param email
     * @param callback
     * @receiver
     */
    fun getUserProfileByEmail(
        email: String,
        callback: (ProfileData?, Throwable?) -> Unit
    )

    /**
     * Update user profile by i d
     *
     * @param id
     * @param map
     * @param callback
     * @receiver
     */
    fun updateUserProfileByID(
        id: Int,
        map: Map<String, String>,
        callback: (Int?, Throwable?) -> Unit
    )

    /**
     * Delete user profile by i d
     *
     * @param id
     * @param callback
     * @receiver
     */
    fun deleteUserProfileByID(id: Int, callback: (Int?, Throwable?) -> Unit)

}
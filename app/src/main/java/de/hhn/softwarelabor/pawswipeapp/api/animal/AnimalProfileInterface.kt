package de.hhn.softwarelabor.pawswipeapp.api.animal

import com.google.gson.JsonObject
import de.hhn.softwarelabor.pawswipeapp.api.data.ProfileData

/**
 * Animal profile interface
 *
 *
 */
interface AnimalProfileInterface {

    /**
     * Create animal profile
     *
     * @param name
     * @param species
     * @param birthday
     * @param illness
     * @param description
     * @param breed
     * @param color
     * @param gender
     * @param profile
     * @param callback
     * @receiver
     */
    fun createAnimalProfile(name: String?,
                            species: String?,
                            birthday: String?,
                            illness: String?,
                            description: String?,
                            breed: String?,
                            color: String?,
                            gender: String?,
                            profile_id: ProfileData,
                            callback: (Int?, Throwable?) -> Unit)

    /**
     * Get all animal profile ids
     *
     * @param callback
     * @receiver
     */
    fun getAllAnimalProfileIDs(callback: (List<String>?, Throwable?) -> Unit)

    /**
     * Get animal profile by id
     *
     * @param id
     * @param callback
     * @receiver
     */
    fun getAnimalProfileByID(id: Int, callback: (JsonObject?, Throwable?) -> Unit)

    /**
     * Update animal profile by id
     *
     * @param id
     * @param map
     * @param callback
     * @receiver
     */
    fun updateAnimalProfileByID(id: Int, map: Map<String, String>, callback: (Int?, Throwable?) -> Unit)

    /**
     * Delete user profile by id
     *
     * @param id
     * @param callback
     * @receiver
     */
    fun deleteUserProfileByID(id: Int, callback: (Int?, Throwable?) -> Unit)
}
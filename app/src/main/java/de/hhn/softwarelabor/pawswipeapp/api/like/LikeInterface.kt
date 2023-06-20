package de.hhn.softwarelabor.pawswipeapp.api.like

import okhttp3.Response

/**
 * LikeInterface defines the methods for liking, unliking, and fetching liked animals for a specific profile.
 *
 * @author Felix Kuhbier
 * @since 10.6.2023
 */
interface LikeInterface {

    /**
     * Like an animal for a specific profile.
     *
     * @param profile_id The ID of the profile for which to add the liked animal.
     * @param animal_id The ID of the animal to be liked.
     * @param callback A callback function that takes two parameters:
     *                 1. A `String?` representing the response body, or `null` if an error occurred.
     *                 2. A `Throwable?` representing the error that occurred, or `null` if the request was successful.
     * @receiver The instance of the class implementing this method.
     */
    fun likeAnimal(profile_id: Int, animal_id: Int, callback: (Response?, Throwable?) -> Unit)

    /**
     * Unlike an animal for a specific profile.
     *
     * @param profile_id The ID of the profile for which to remove the liked animal.
     * @param animal_id The ID of the animal to be disliked.
     * @param callback A callback function that takes two parameters:
     *                 1. A `String?` representing the response body, or `null` if an error occurred.
     *                 2. A `Throwable?` representing the error that occurred, or `null` if the request was successful.
     * @receiver The instance of the class implementing this method.
     */
    fun dislikeAnimal(profile_id: Int, animal_id: Int, callback: (Response?, Throwable?) -> Unit)

    /**
     * Get liked animals for a specific profile.
     *
     * @param profile_id The ID of the profile for which to fetch liked animals.
     * @param callback A callback function that takes two parameters:
     *                 1. An `Array<Int>?` representing the liked animal IDs, or `null` if an error occurred.
     *                 2. A `Throwable?` representing the error that occurred, or `null` if the request was successful.
     * @receiver The instance of the class implementing this method.
     */
    fun getLikedAnimals(profile_id: Int, callback: (Array<Int>?, Throwable?) -> Unit)
}

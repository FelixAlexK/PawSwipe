package de.hhn.softwarelabor.pawswipeapp.api.like

import android.content.ContentValues
import android.util.Log
import com.google.gson.Gson
import de.hhn.softwarelabor.pawswipeapp.api.data.LikeData

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException


private const val BASE_URL = "http://193.196.55.115:8081/liked-animals"

/**
 * LikeApi class is responsible for managing the liking and disliking of animals for a specific profile.
 *
 * @author Felix Kuhbier
 * @since 10.6.2023
 */
class LikeApi : LikeInterface {

    private var client: OkHttpClient = OkHttpClient()
    private var gson = Gson()

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
    override fun likeAnimal(
        profile_id: Int,
        animal_id: Int,
        callback: (Response?, Throwable?) -> Unit
    ) {

        val likeData = LikeData(profile_id, animal_id)
        val json = gson.toJson(likeData)
        val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())


        val request = Request.Builder()
            .url("$BASE_URL/like")
            .post(body)
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {

                        callback(response, null)
                    } else {
                        callback(null, Exception("Error: ${response.code}"))
                    }
                    Log.d("PawSwipe", "Liked: ${response.code}")
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e(ContentValues.TAG, "Request failed", e)
                    callback(null, e)
                }
            })
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }
    }

    /**
     * Dislike an animal for a specific profile.
     *
     * @param profile_id The ID of the profile for which to remove the liked animal.
     * @param animal_id The ID of the animal to be disliked.
     * @param callback A callback function that takes two parameters:
     *                 1. A `String?` representing the response body, or `null` if an error occurred.
     *                 2. A `Throwable?` representing the error that occurred, or `null` if the request was successful.
     * @receiver The instance of the class implementing this method.
     */
    override fun dislikeAnimal(
        profile_id: Int,
        animal_id: Int,
        callback: (Response?, Throwable?) -> Unit
    ) {
        val likeData = LikeData(profile_id, animal_id)
        val json = gson.toJson(likeData)
        val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())


        val request = Request.Builder()
            .url("$BASE_URL/dislike")
            .delete(body)
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {

                        callback(response, null)
                    } else {
                        callback(null, Exception("Error: ${response.code}"))
                    }
                    Log.d("PawSwipe", "Disliked: ${response.code}")
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e(ContentValues.TAG, "Request failed", e)
                    callback(null, e)
                }
            })
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }
    }

    /**
     * Get liked animals for a specific profile.
     *
     * @param profile_id The ID of the profile for which to fetch liked animals.
     * @param callback A callback function that takes two parameters:
     *                 1. An `Array<Int>?` representing the liked animal IDs, or `null` if an error occurred.
     *                 2. A `Throwable?` representing the error that occurred, or `null` if the request was successful.
     * @receiver The instance of the class implementing this method.
     */
    override fun getLikedAnimals(profile_id: Int, callback: (Array<Int>?, Throwable?) -> Unit) {
        val request = Request.Builder()
            .url("$BASE_URL/list/$profile_id")
            .get()
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {
                        val jsonArray = JSONArray(responseBody)
                        val intArray = Array(jsonArray.length()) { i -> jsonArray.getInt(i) }
                        callback(intArray, null)
                    } else {
                        callback(null, Exception("Error: ${response.code}"))
                    }
                    Log.d("PawSwipe", "Get liked: ${response.code}")
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e(ContentValues.TAG, "Request failed", e)
                    callback(null, e)
                }
            })
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }
    }
}
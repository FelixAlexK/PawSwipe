package de.hhn.softwarelabor.pawswipeapp.api

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.util.Date


/**
 * [UserProfileApi], provides functions to do http requests
 *  @author Felix Kuhbier
 *  @since 2023.04.19
 */
class UserProfileApi {
    private var client: OkHttpClient = OkHttpClient()
    private var baseUrl = "http://45.146.253.199:8080/profile"
    private var gson = Gson()

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
     * @property isCompleted is this profile completed?
     * @property birthday
     * @property phoneNumber phone-number of the shelter
     * @property openingHours opening hours of the shelter
     * @property street name of the street where the shelter is located
     * @property country country in which the shelter is located
     * @property city city in which the shelter is located
     * @property streetNumber number of street in which the shelter is located
     * @property homepage homepage of the shelter
     * @property postalCode postal code of the shelters location
     * @property discriminator is it a "user profile" or a "shelter profile"?
     */
    data class ShelterProfileData(
        val profileId: Int,
        val username: String?,
        val profilePicture: ByteArray?,
        val description: String?,
        val password: String,
        val creationDate: Date?,
        val email: String,
        val isCompleted: Boolean?,
        val birthday: Date?,
        val phoneNumber: String?,
        val openingHours: String?,
        val street: String?,
        val country: String?,
        val city: String?,
        val streetNumber: Int?,
        val homepage: String?,
        val postalCode: Int?,
        val discriminator: String
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ShelterProfileData

            if (profileId != other.profileId) return false
            if (username != other.username) return false
            if (profilePicture != null) {
                if (other.profilePicture == null) return false
                if (!profilePicture.contentEquals(other.profilePicture)) return false
            } else if (other.profilePicture != null) return false
            if (description != other.description) return false
            if (password != other.password) return false
            if (creationDate != other.creationDate) return false
            if (email != other.email) return false
            if (isCompleted != other.isCompleted) return false
            if (birthday != other.birthday) return false
            if (phoneNumber != other.phoneNumber) return false
            if (openingHours != other.openingHours) return false
            if (street != other.street) return false
            if (country != other.country) return false
            if (city != other.city) return false
            if (streetNumber != other.streetNumber) return false
            if (homepage != other.homepage) return false
            if (postalCode != other.postalCode) return false
            if (discriminator != other.discriminator) return false

            return true
        }

        override fun hashCode(): Int {
            var result = profileId
            result = 31 * result + username.hashCode()
            result = 31 * result + (profilePicture?.contentHashCode() ?: 0)
            result = 31 * result + description.hashCode()
            result = 31 * result + password.hashCode()
            result = 31 * result + (creationDate?.hashCode() ?: 0)
            result = 31 * result + email.hashCode()
            result = 31 * result + isCompleted.hashCode()
            result = 31 * result + (birthday?.hashCode() ?: 0)
            result = 31 * result + phoneNumber.hashCode()
            result = 31 * result + openingHours.hashCode()
            result = 31 * result + street.hashCode()
            result = 31 * result + country.hashCode()
            result = 31 * result + city.hashCode()
            result = 31 * result + streetNumber!!
            result = 31 * result + homepage.hashCode()
            result = 31 * result + postalCode!!
            result = 31 * result + discriminator.hashCode()
            return result
        }

    }

    /**
     * [UserProfileData], holds data for a user profile
     *
     * @property username name of the shelter
     * @property profilePicture picture of the shelter
     * @property description description of the shelter
     * @property password password of the profile
     * @property creationDate date on which the profile was created
     * @property email email of the shelter
     * @property isCompleted is this profile completed?
     * @property birthday
     * @property phoneNumber phone-number of the shelter
     * @property openingHours opening hours of the shelter
     * @property street name of the street where the shelter is located
     * @property country country in which the shelter is located
     * @property city city in which the shelter is located
     * @property streetNumber number of street in which the shelter is located
     * @property homepage homepage of the shelter
     * @property postalCode postal code of the shelters location
     * @property discriminator is it a "user profile" or a "shelter profile"?
     */
    data class UserProfileData(
        val username: String?,
        val profilePicture: Array<Byte>?,
        val description: String?,
        val password: String,
        val creationDate: Date?,
        val email: String,
        val isCompleted: Boolean?,
        val birthday: Date?,
        val phoneNumber: String?,
        val openingHours: String?,
        val street: String?,
        val country: String?,
        val city: String?,
        val streetNumber: Int?,
        val homepage: String?,
        val postalCode: Int?,
        val discriminator: String
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as UserProfileData

            if (username != other.username) return false
            if (profilePicture != null) {
                if (other.profilePicture == null) return false
                if (!profilePicture.contentEquals(other.profilePicture)) return false
            } else if (other.profilePicture != null) return false
            if (description != other.description) return false
            if (password != other.password) return false
            if (creationDate != other.creationDate) return false
            if (email != other.email) return false
            if (isCompleted != other.isCompleted) return false
            if (birthday != other.birthday) return false
            if (phoneNumber != other.phoneNumber) return false
            if (openingHours != other.openingHours) return false
            if (street != other.street) return false
            if (country != other.country) return false
            if (city != other.city) return false
            if (streetNumber != other.streetNumber) return false
            if (homepage != other.homepage) return false
            if (postalCode != other.postalCode) return false
            if (discriminator != other.discriminator) return false

            return true
        }

        override fun hashCode(): Int {
            var result = username.hashCode()
            result = 31 * result + (profilePicture?.contentHashCode() ?: 0)
            result = 31 * result + description.hashCode()
            result = 31 * result + password.hashCode()
            result = 31 * result + (creationDate?.hashCode() ?: 0)
            result = 31 * result + email.hashCode()
            result = 31 * result + isCompleted.hashCode()
            result = 31 * result + (birthday?.hashCode() ?: 0)
            result = 31 * result + phoneNumber.hashCode()
            result = 31 * result + openingHours.hashCode()
            result = 31 * result + street.hashCode()
            result = 31 * result + country.hashCode()
            result = 31 * result + city.hashCode()
            result = 31 * result + streetNumber!!
            result = 31 * result + homepage.hashCode()
            result = 31 * result + postalCode!!
            result = 31 * result + discriminator.hashCode()
            return result
        }
    }


    /**
     * Create user profile
     *
     * @property username name of the shelter
     * @property profilePicture picture of the shelter
     * @property description description of the shelter
     * @property password password of the profile
     * @property creationDate date on which the profile was created
     * @property email email of the shelter
     * @property isCompleted is this profile completed?
     * @property birthday
     * @property phoneNumber phone-number of the shelter
     * @property openingHours opening hours of the shelter
     * @property street name of the street where the shelter is located
     * @property country country in which the shelter is located
     * @property city city in which the shelter is located
     * @property streetNumber number of street in which the shelter is located
     * @property homepage homepage of the shelter
     * @property postalCode postal code of the shelters location
     * @property discriminator is it a "user profile" or a "shelter profile"?
     */
    fun createUserProfile(
        username: String?, profilePicture: Array<Byte>?, description: String?,
        password: String, creationDate: Date?, email: String, isCompleted: Boolean?,
        birthday: Date?, phoneNumber: String?, openingHours: String?, street: String?,
        country: String?, city: String?, streetNumber: Int?, homepage: String?,
        postalCode: Int?, discriminator: String, callback: (Int?, Throwable?) -> Unit
    ) {


        val userProfile = UserProfileData(
            username,
            profilePicture,
            description,
            password,
            creationDate,
            email,
            isCompleted,
            birthday,
            phoneNumber,
            openingHours,
            street,
            country,
            city,
            streetNumber,
            homepage,
            postalCode,
            discriminator
        )

        val profileJson = gson.toJson(userProfile)
        val body = profileJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$baseUrl/create")
            .post(body)
            .build()



        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    callback(response.code, null)
                } else {
                    callback(null, Exception("Error fetching IDs"))
                }
                Log.d("response", response.code.toString())
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed", e)
                callback(null, e)
            }
        })

    }

    /**
     * Get all user profile ids
     *
     */
    fun getAllUserProfileIDs(callback: (List<String>?, Throwable?) -> Unit) {
        val request = Request.Builder()
            .url("$baseUrl/all/ids")
            .get()
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed", e)
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val ids = gson.fromJson(responseBody, Array<String>::class.java).toList()
                    callback(ids, null)
                } else {
                    callback(null, Exception("Error fetching IDs"))
                }
                Log.d("response", response.code.toString())
            }
        })

    }

    /**
     * Get user profile by id
     *
     * @param id ID of user profile
     * @param callback the callback to be invoked when the API response is received. This callback
     *                 provides the response data and status to the caller.
     */
    fun getUserProfileByID(id: Int, callback: (ShelterProfileData?, Throwable?) -> Unit) {


        val request = Request.Builder()
            .url("$baseUrl/$id")
            .get()
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(ContentValues.TAG, "Request failed", e)
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val animalProfileShelter =
                        gson.fromJson(responseBody, ShelterProfileData::class.java)

                    callback(animalProfileShelter, null)
                } else {
                    callback(null, Exception("Error fetching IDs"))
                }
            }

        })
    }


    /**
     * Update user profile by id
     *
     * @param id ID of user profile
     * @param map Map collection with the contents to be updated
     */
    fun updateUserProfileByID(id: Int, map: Map<String, String>, callback: (Int?, Throwable?) -> Unit) {


        val updateJson = gson.toJson(map)
        val body = updateJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$baseUrl/update/$id")
            .put(body)
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed", e)
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if(response.isSuccessful && responseBody != null){
                    callback(response.code, null)
                }else {
                    callback(null, Exception("Error fetching IDs"))
                }
                Log.d("response", response.code.toString())

            }

        })


    }

    /**
     * Delete user profile by id
     *
     * @param id ID of user profile
     */
    fun deleteUserProfileByID(id: Int, callback: (Int?, Throwable?) -> Unit) {


        val request = Request.Builder()
            .url("$baseUrl/$id")
            .delete()
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed", e)
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if(response.isSuccessful && responseBody != null){
                    callback(response.code, null)
                }else {
                    callback(null, Exception("Error fetching IDs"))
                }
                Log.d("response", response.code.toString())

            }

        })


    }


}
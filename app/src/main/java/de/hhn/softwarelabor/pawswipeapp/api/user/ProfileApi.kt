package de.hhn.softwarelabor.pawswipeapp.api.user

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.Gson
import de.hhn.softwarelabor.pawswipeapp.api.data.ProfileData
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException


/**
 * [ProfileApi], provides functions to do http requests
 *  @author Felix Kuhbier
 *  @since 2023.05.06
 */

private const val BASE_URL = "http://193.196.55.115:8081/profile"
//private const val BASE_URL = "http://45.146.253.199:8080/profile"
class ProfileApi : ProfileInterface {
    private var client: OkHttpClient = OkHttpClient()
    private var gson = Gson()
    override fun createUserProfile(
        profile_id: Int?,
        username: String,
        email: String,
        phone_number: String?,
        profile_picture: String?,
        description: String?,
        password: String,
        creation_date: String?,
        birthday: String?,
        opening_hours: String?,
        street: String?,
        country: String?,
        city: String?,
        street_number: String?,
        homepage: String?,
        postal_code: String?,
        firstname: String,
        lastname: String,
        lat: Double?,
        lon: Double?,
        discriminator: String,
        callback: (ProfileData?, Throwable?) -> Unit
    ) {
        val userProfile = ProfileData(
            profile_id,
            username,
            email,
            phone_number,
            profile_picture,
            description,
            password,
            creation_date,
            birthday,
            opening_hours,
            street,
            country,
            city,
            street_number,
            homepage,
            postal_code,
            firstname,
            lastname,
            lat,
            lon,
            discriminator
        )

        val userProfileJson = gson.toJson(userProfile)
        val body = userProfileJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$BASE_URL/create")
            .post(body)
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {
                        val profileJson =
                            gson.fromJson(responseBody, ProfileData::class.java)
                        callback(profileJson, null)
                    } else {
                        callback(null, Exception("Error creating profile"))
                    }
                    Log.d("response", response.code.toString())
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "Request failed", e)
                    callback(null, e)
                }
            })
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }
    }

    override fun getAllUserProfileIDs(callback: (List<String>?, Throwable?) -> Unit) {
        val request = Request.Builder()
            .url("$BASE_URL/all/ids")
            .get()
            .build()

        try {
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
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }
    }

    override fun getUserProfileByID(
        id: Int,
        callback: (ProfileData?, Throwable?) -> Unit
    ) {
        val request = Request.Builder()
            .url("$BASE_URL/id/$id")
            .get()
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(ContentValues.TAG, "Request failed", e)
                    callback(null, e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {
                        val profileJson =
                            gson.fromJson(responseBody, ProfileData::class.java)

                        callback(profileJson, null)
                    } else {
                        callback(null, Exception("Error fetching ID"))
                    }
                }

            })
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }
    }

    override fun getUserProfileByEmail(
        email: String,
        callback: (ProfileData?, Throwable?) -> Unit
    ) {
        val request = Request.Builder()
            .url("$BASE_URL/email/$email")
            .get()
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(ContentValues.TAG, "Request failed", e)
                    callback(null, e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {
                        val profileJson =
                            gson.fromJson(responseBody, ProfileData::class.java)

                        callback(profileJson, null)
                    } else {
                        callback(null, Exception("Error fetching email"))
                    }
                }

            })
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }
    }

    override fun updateUserProfileByID(
        id: Int,
        map: Map<String, String>,
        callback: (Int?, Throwable?) -> Unit
    ) {
        val updateUserProfileJson = gson.toJson(map)
        val body = updateUserProfileJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$BASE_URL/update/$id")
            .put(body)
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "Request failed", e)
                    callback(null, e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {
                        callback(response.code, null)
                    } else {
                        callback(null, Exception("Error fetching IDs"))
                    }
                    Log.d("response", response.code.toString())

                }

            })
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }

    }

    override fun deleteUserProfileByID(id: Int, callback: (Int?, Throwable?) -> Unit) {
        val request = Request.Builder()
            .url("$BASE_URL/$id")
            .delete()
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "Request failed", e)
                    callback(null, e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {
                        callback(response.code, null)
                    } else {
                        callback(null, Exception("Error fetching IDs"))
                    }
                    Log.d("response", response.code.toString())

                }

            })
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }
    }


}
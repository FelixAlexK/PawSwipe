package de.hhn.softwarelabor.pawswipeapp.api

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


class UserProfileApi {
    private var client: OkHttpClient = OkHttpClient()
    private var baseUrl = "http://45.146.253.199:8080/profile"
    private var gson = Gson()

    data class UserProfile(
        val username: String,
        val profilePicture: Array<Byte>?,
        val description: String,
        val password: String,
        val creationDate: Date?,
        val email: String,
        val isCompleted: Boolean,
        val birthday: Date?,
        val phoneNumber: String,
        val openingHours: String,
        val street: String,
        val country: String,
        val city: String,
        val streetNumber: Int,
        val homepage: String,
        val postalCode: Int,
        val discriminator: String
    )


    fun createUserProfile(
        username: String, profilePicture: Array<Byte>?, description: String,
        password: String, creationDate: Date?, email: String, isCompleted: Boolean,
        birthday: Date?, phoneNumber: String, openingHours: String, street: String,
        country: String, city: String, streetNumber: Int, homepage: String,
        postalCode: Int, discriminator: String
    ) {


        val userProfile = UserProfile(
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
                response.code
                Log.d(TAG, "Response body: $responseBody")
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed", e)
            }
        })

    }

    fun getAllUserProfileIDs() {
        val request = Request.Builder()
            .url("$baseUrl/all/ids")
            .get()
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                response.code
                Log.d("ids", responseBody.toString())
            }
        })

    }

    suspend fun getUserProfileByID(id: Int) {


        val request = Request.Builder()
            .url("$baseUrl/$id")
            .get()
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                response.code
                Log.d("ids", responseBody.toString())
            }

        })


    }

    fun updateUserProfileByID(id: Int, map: Map<String, String>) {


        val updateJson = gson.toJson(map)
        val body = updateJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$baseUrl/update/$id")
            .put(body)
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                response.code
                Log.d("ids", responseBody.toString())
            }

        })


    }

    fun deleteUserProfileByID(id: Int) {


        val request = Request.Builder()
            .url("$baseUrl/$id")
            .delete()
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                response.code
                Log.d("ids", response.code.toString())
            }

        })


    }


}
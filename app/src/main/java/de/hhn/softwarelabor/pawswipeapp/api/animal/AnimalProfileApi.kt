package de.hhn.softwarelabor.pawswipeapp.api.animal

import android.content.ContentValues
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import de.hhn.softwarelabor.pawswipeapp.api.data.AnimalProfileData
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
 * [AnimalProfileApi], provides functions to do http requests
 * @author Felix Kuhbier
 * @since 2023.05.05
 */
private const val BASE_URL = "http://45.146.253.199:8080/animal"
class AnimalProfileApi : AnimalProfileInterface {

    private var client: OkHttpClient = OkHttpClient()
    private var gson = Gson()
    override fun createAnimalProfile(
        name: String?,
        species: String?,
        birthday: String?,
        illness: String?,
        description: String?,
        breed: String?,
        color: String?,
        gender: String?,
        profile_id: ProfileData,
        callback: (Int?, Throwable?) -> Unit
    ) {
        val animalProfileData = AnimalProfileData(
            name, species, birthday, illness, description, breed, color, gender, profile_id
        )


        val animalProfileJson = gson.toJson(animalProfileData)
        val body = animalProfileJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$BASE_URL/create")
            .post(body)
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
                        callback(response.code, null)
                    }else {
                        callback(null, Exception(response.code.toString()))
                    }
                    Log.d("response", response.code.toString())
                }
            })
        }catch (e: IllegalStateException){
            throw IllegalStateException("An error has occurred: $e")
        }

    }

    override fun getAllAnimalProfileIDs(callback: (List<String>?, Throwable?) -> Unit) {
        val request = Request.Builder()
            .url("$BASE_URL/all/ids")
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
                        val ids = gson.fromJson(responseBody, Array<String>::class.java).toList()
                        callback(ids, null)
                    } else {
                        callback(null, Exception("Error fetching IDs"))
                    }
                    Log.d("response", response.code.toString())
                }
            })
        }catch (e: IllegalStateException){
            throw IllegalStateException("An error has occurred: $e")
        }
    }

    override fun getAnimalProfileByID(id: Int, callback: (JsonObject?, Throwable?) -> Unit) {
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
                    if(response.isSuccessful && responseBody != null){

                        val jsonObject = Gson().fromJson(responseBody, JsonObject::class.java)
                        callback(jsonObject, null)
                    }else {
                        callback(null, Exception("Error fetching IDs"))
                    }
                    Log.d("response", response.code.toString())
                }

            })
        }catch (e: IllegalStateException){
            throw IllegalStateException("An error has occurred: $e")
        }

    }

    override fun updateAnimalProfileByID(
        id: Int,
        map: Map<String, String>,
        callback: (Int?, Throwable?) -> Unit
    ) {
        val updateJson = gson.toJson(map)
        val body = updateJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$BASE_URL/update/$id")
            .put(body)
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(ContentValues.TAG, "Request failed", e)
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
        }catch (e: IllegalStateException){
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
                    Log.e(ContentValues.TAG, "Request failed", e)
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
        }catch (e: IllegalStateException){
            throw IllegalStateException("An error has occurred: $e")
        }
    }


}


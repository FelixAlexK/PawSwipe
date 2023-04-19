package de.hhn.softwarelabor.pawswipeapp.api

import android.content.ContentValues
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.time.LocalDate

/**
 * [AnimalProfileApi], provides functions to do http requests
 * @author Felix Kuhbier
 * @since 2023.04.19
 */
class AnimalProfileApi {

    private var client: OkHttpClient = OkHttpClient()
    private var baseUrl = "http://45.146.253.199:8080/animal"
    private var gson = Gson()


    /**
     * [AnimalProfile], holds data for an animal profile
     *
     * @property name
     * @property species
     * @property birthday
     * @property illness
     * @property description
     * @property breed
     * @property color
     * @property gender
     * @property profile
     */
    data class AnimalProfile(
        val name: String?,
        val species: String?,
        val birthday: LocalDate?,
        val illness: String?,
        val description: String?,
        val breed: String?,
        val color: String?,
        val gender: String?,
        val profile: UserProfileApi.ShelterProfileData
    )


    /**
     * Create animal profile
     *
     * @param [name] name of the animal
     * @param [species] species of the animal
     * @param [birthday] Birthday date of the animal
     * @param [illness] Diseases of the animal
     * @param [description] description of the animal
     * @param [breed] breed of the animal
     * @param [color] color of the animal
     * @param [gender] gender of the animal
     * @param [profile] shelter profile
     */
    fun createAnimalProfile(
        name: String?,
        species: String?,
        birthday: LocalDate?,
        illness: String?,
        description: String?,
        breed: String?,
        color: String?,
        gender: String?,
        profile: UserProfileApi.ShelterProfileData?,
        callback: (Int?, Throwable?) -> Unit
    ) {


        val animalProfile = AnimalProfile(
            name, species, birthday, illness, description, breed, color, gender, profile!!
        )


        val profileJson = gson.toJson(animalProfile)
        val body = profileJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$baseUrl/create")
            .post(body)
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(ContentValues.TAG, "Request failed", e)
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


    }

    /**
     * Get all animal profile ids
     *
     */
    fun getAllAnimalProfileIDs(callback: (List<String>?, Throwable?) -> Unit) {
        val request = Request.Builder()
            .url("$baseUrl/all/ids")
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
     * Get animal profile by id
     *
     * @param id ID of the animal profile
     */
    fun getAnimalProfileByID(id: Int, callback: (JsonObject?, Throwable?) -> Unit) {


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
                if(response.isSuccessful && responseBody != null){

                    val jsonObject = Gson().fromJson(responseBody, JsonObject::class.java)
                    callback(jsonObject, null)
                }else {
                    callback(null, Exception("Error fetching IDs"))
                }
                Log.d("response", response.code.toString())
            }

        })
    }

    /**
     * Update animal profile by id
     *
     * @param id ID of the animal profile
     * @param map Map collection with the contents to be updated
     */
    fun updateAnimalProfileByID(id: Int, map: Map<String, String>, callback: (Int?, Throwable?) -> Unit) {


        val updateJson = gson.toJson(map)
        val body = updateJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$baseUrl/update/$id")
            .put(body)
            .build()


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


    }

    /**
     * Delete user profile by id
     *
     * @param id ID of the animal profile
     */
    fun deleteUserProfileByID(id: Int, callback: (Int?, Throwable?) -> Unit) {


        val request = Request.Builder()
            .url("$baseUrl/$id")
            .delete()
            .build()


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


    }
}

/* Example Code for creating a Animal
* private fun test() = runBlocking {
        val animalProfileApi = AnimalProfileApi()
        val userProfileApi = UserProfileApi()

        launch {
           val send = async {

                userProfileApi.getUserProfileByID(29, object : UserProfileCallback {
                    override fun onResponse(
                        userProfile: UserProfileApi.UserProfileAnimal?,
                        response: Response
                    ) {
                        animalProfileApi.createAnimalProfile(
                            "Mozart", "Cat", null,
                            null, "Black Cat", "n/a", "black", "male", userProfile!!


                        )

                    }

                    override fun onFailure() {
                        Log.e(ContentValues.TAG, "Request failed")
                    }

                })





            }

            send.await()

        }
*/
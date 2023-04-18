package de.hhn.softwarelabor.pawswipeapp.api

import android.content.ContentValues
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
import java.time.LocalDate

interface AnimalProfileCallback {
    fun onResponse(responseBody: String)
    fun onFailure()
}

class AnimalProfileApi {

    private var client: OkHttpClient = OkHttpClient()
    private var baseUrl = "http://45.146.253.199:8080/animal"
    private var gson = Gson()


    data class AnimalProfile(
        val name: String?,
        val species: String?,
        val birthday: LocalDate?,
        val illness: String?,
        val description: String?,
        val breed: String?,
        val color: String?,
        val gender: String?,
        val profile: UserProfileApi.UserProfileAnimal
    )


    fun createAnimalProfile(
        name: String?,
        species: String?,
        birthday: LocalDate?,
        illness: String?,
        description: String?,
        breed: String?,
        color: String?,
        gender: String?,
        profile: UserProfileApi.UserProfileAnimal
    ) {


        val animalProfile = AnimalProfile(
            name, species, birthday, illness, description, breed, color, gender, profile
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
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                Log.d("response", response.code.toString())
            }
        })


    }

    fun getAnimalProfileByID(id: Int) {


        val request = Request.Builder()
            .url("$baseUrl/$id")
            .get()
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(ContentValues.TAG, "Request failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                Log.d("response", response.code.toString())
            }

        })
    }

    fun updateAnimalProfileByID(id: Int, map: Map<String, String>) {


        val updateJson = gson.toJson(map)
        val body = updateJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$baseUrl/update/$id")
            .put(body)
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(ContentValues.TAG, "Request failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                Log.d("response", response.code.toString())
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
                Log.e(ContentValues.TAG, "Request failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
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
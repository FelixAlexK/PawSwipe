package de.hhn.softwarelabor.pawswipeapp.api

import android.content.ContentValues
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.time.LocalDate

class AnimalProfileApi {

    private var client: OkHttpClient = OkHttpClient()
    private var baseUrl = "http://45.146.253.199:8080/animal"
    private var gson = Gson()


    private data class AnimalProfile(
        val name: String?,
        val species: String?,
        val birthday: LocalDate?,
        val illness: String?,
        val description: String?,
        val breed: String?,
        val color: String?,
        val gender: String?,
        val profile: Unit
    )

    suspend fun createAnimalProfile(
        name: String?, species: String?, birthday: LocalDate?, illness: String?,
        description: String?,
        breed: String?, color: String?, gender: String?, profile: Unit
    ) {

        val animalProfile = AnimalProfile(
            name, species, birthday, illness, description, breed, color, gender, profile
        )


        val animalProfileJson = gson.toJson(animalProfile)
        val body = animalProfileJson.toRequestBody("application/json; charset=utf-8".toMediaType())
        Log.i("json", animalProfileJson)

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
                response.code
                Log.d("ids", responseBody.toString())
            }
        })


    }

    fun getAnimalProfileByID(id: Int){
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
                response.code
                Log.d("ids", responseBody.toString())
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
                response.code
                Log.d("ids", responseBody.toString())
            }

        })


    }

    fun deleteAnimalProfileByID(id: Int) {


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
                response.code
                Log.d("ids", response.code.toString())
            }

        })


    }
}
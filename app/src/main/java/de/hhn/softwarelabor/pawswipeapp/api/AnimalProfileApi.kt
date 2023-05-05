package de.hhn.softwarelabor.pawswipeapp.api

import android.content.ContentValues
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import de.hhn.softwarelabor.pawswipeapp.api.data.ShelterProfileData
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
        profile: ShelterProfileData?,
        callback: (Int?, Throwable?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getAllAnimalProfileIDs(callback: (List<String>?, Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAnimalProfileByID(id: Int, callback: (JsonObject?, Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateAnimalProfileByID(
        id: Int,
        map: Map<String, String>,
        callback: (Int?, Throwable?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteUserProfileByID(id: Int, callback: (Int?, Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }


}


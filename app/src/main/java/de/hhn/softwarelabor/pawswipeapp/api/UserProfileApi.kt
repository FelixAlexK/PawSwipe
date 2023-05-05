package de.hhn.softwarelabor.pawswipeapp.api

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.Gson
import de.hhn.softwarelabor.pawswipeapp.api.data.ShelterProfileData
import de.hhn.softwarelabor.pawswipeapp.api.data.UserProfileData
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

private const val BASE_URL = "http://45.146.253.199:8080/profile"
class UserProfileApi : UserProfileInterface {
    private var client: OkHttpClient = OkHttpClient()
    private var gson = Gson()
    override fun createUserProfile(
        username: String?,
        profilePicture: Array<Byte>?,
        description: String?,
        password: String,
        creationDate: Date?,
        email: String,
        birthday: String?,
        phoneNumber: String?,
        openingHours: String?,
        street: String?,
        country: String?,
        city: String?,
        streetNumber: Int?,
        homepage: String?,
        postalCode: String?,
        firstname: String,
        lastname: String,
        discriminator: String,
        callback: (Int?, Throwable?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getAllUserProfileIDs(callback: (List<String>?, Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getUserProfileByID(
        id: Int,
        callback: (ShelterProfileData?, UserProfileData?, Throwable?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun updateUserProfileByID(
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
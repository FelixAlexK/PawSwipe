package de.hhn.softwarelabor.pawswipeapp.api.filter

import android.content.ContentValues
import android.util.Log
import com.google.gson.Gson
import de.hhn.softwarelabor.pawswipeapp.api.data.AnimalProfileData
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

private const val BASE_URL = "http://193.196.55.115:8081/animal/filter?"

class FilterApi : FilterInterface {

    private var client: OkHttpClient = OkHttpClient()
    private var gson = Gson()

    override fun filterAnimal(
        map: MutableMap<FilterEnum, String>,
        callback: (List<AnimalProfileData>?, Throwable?) -> Unit
    ) {

        val filterOptions = ArrayList<String>()
        val urlBuilder = StringBuilder()
        var finalUrl = ""
        map.forEach { entry ->
            when (entry.key) {
                FilterEnum.AGE_MAX -> filterOptions.add("${entry.key.filter}=${entry.value}")
                FilterEnum.AGE_MIN -> filterOptions.add("${entry.key.filter}=${entry.value}")
                FilterEnum.BREED -> filterOptions.add("${entry.key.filter}=${entry.value}")
                FilterEnum.COLOR -> filterOptions.add("${entry.key.filter}=${entry.value}")
                FilterEnum.GENDER -> filterOptions.add("${entry.key.filter}=${entry.value}")
                FilterEnum.ILLNESS -> filterOptions.add("${entry.key.filter}=${entry.value}")
                FilterEnum.PROFILE_ID -> filterOptions.add("${entry.key.filter}=${entry.value}")
                FilterEnum.SPECIES -> filterOptions.add("${entry.key.filter}=${entry.value}")
            }
        }

        if (filterOptions.isNotEmpty()) {
            urlBuilder.append(filterOptions[0])

            if (filterOptions.size > 1) {
                var i = 1
                while (i < filterOptions.size) {
                    urlBuilder.append("&")
                    urlBuilder.append(filterOptions[i])
                    i++
                }
            }

            finalUrl = urlBuilder.toString()
        }


        val request = Request.Builder()
            .url("http://193.196.55.115:8081/animal/filter?$finalUrl")
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
                        val array =
                            gson.fromJson(responseBody, Array<AnimalProfileData>::class.java)
                                .toList()
                        callback(array, null)
                    } else {
                        callback(null, FilterException("Filtering was not possible"))
                    }
                    Log.d("response", response.code.toString())
                }
            })
        } catch (e: IllegalStateException) {
            throw IllegalStateException("An error has occurred: $e")
        }
    }


}

package de.hhn.softwarelabor.pawswipeapp.api

import com.google.gson.JsonObject
import de.hhn.softwarelabor.pawswipeapp.api.data.ShelterProfileData

interface AnimalProfileInterface {

    fun createAnimalProfile(name: String?,
                            species: String?,
                            birthday: String?,
                            illness: String?,
                            description: String?,
                            breed: String?,
                            color: String?,
                            gender: String?,
                            profile: ShelterProfileData?,
                            callback: (Int?, Throwable?) -> Unit)

    fun getAllAnimalProfileIDs(callback: (List<String>?, Throwable?) -> Unit)

    fun getAnimalProfileByID(id: Int, callback: (JsonObject?, Throwable?) -> Unit)

    fun updateAnimalProfileByID(id: Int, map: Map<String, String>, callback: (Int?, Throwable?) -> Unit)

    fun deleteUserProfileByID(id: Int, callback: (Int?, Throwable?) -> Unit)
}
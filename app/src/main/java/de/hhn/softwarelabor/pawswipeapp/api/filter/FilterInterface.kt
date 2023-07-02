package de.hhn.softwarelabor.pawswipeapp.api.filter

import de.hhn.softwarelabor.pawswipeapp.api.data.AnimalProfileData

interface FilterInterface {

    fun filterAnimal(
        map: MutableMap<FilterEnum, String>,
        callback: (List<AnimalProfileData>?, Throwable?) -> Unit
    )


}
package de.hhn.softwarelabor.pawswipeapp

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.location.Address
import android.location.Geocoder
import android.util.Log

/**
 * To use this class, you can create an instance of GeocodingHelper and call the
 * getLatLngFromAddress() function, passing in the user's manually inputted address as a parameter.
 * The function will return a Pair object containing the latitude and longitude coordinates, or null
 * if the coordinates couldn't be retrieved.
 */
class LatLongUtil(private val context: Context) {

    fun getLatLongFromAddress(address: String): Pair<Double, Double>? {
        val geocoder = Geocoder(context)
        try {
            val addresses: List<Address> = geocoder.getFromLocationName(address, 1) as List<Address>
            if (addresses.isNotEmpty()) {
                val location = addresses[0]
                val latitude = location.latitude
                val longitude = location.longitude
                Log.i(MatchActivity::javaClass.name, "Latitude" + latitude + "Longtitude:" + longitude)
                return Pair(latitude, longitude)

            }
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return null
    }
    /*
    // Example:
    // val address = "1600 Amphitheatre Parkway, Mountain View, CA"
    fun convertAdressInputToOneAdress() {
        // street: String?,
        // country: String?,
        // city: String?,
        // street_number: String?,
        // postal_code: String?, - Postleitzahl
        val completeAdress = "" + postal_code +
    }
     */
}
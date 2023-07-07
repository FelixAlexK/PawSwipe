package de.hhn.softwarelabor.pawswipeapp

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.location.Address
import android.location.Geocoder
import android.util.Log

/**
 * This class provides functionality for determining latitude and longitude from a
 * given address.
 * To use this class, you can create an instance of GeocodingHelper and call the
 * getLatLngFromAddress() function.
 * @author Nico Wendler
 */
class LatLongUtil(private val context: Context) {

    /**
     * Provides latitude and longitude from a given address.
     *
     * @param address the user's manually inputted address
     *              For example: "1600 Amphitheatre Parkway, Mountain View, CA"
     *              Even works with incomplete addresses such as "Heilbronn" only.
     * @return a Pair object containing the latitude and longitude coordinates, or null
     *          if the coordinates couldn't be retrieved.
     */
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
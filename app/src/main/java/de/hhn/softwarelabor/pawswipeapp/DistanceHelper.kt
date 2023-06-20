package de.hhn.softwarelabor.pawswipeapp

import java.lang.Math.*
import java.text.DecimalFormat

class DistanceHelper {
    private fun getShelterInRange(
        currentLat: Double,
        currentLong: Double,
        distance: Int,
        lat: Double,
        lon: Double
    ): Boolean {
        val earthRadius = 6371.0 // Radius der Erde in Kilometern
        val dLat = toRadians(lat - currentLat)
        val dLon = toRadians(lon - currentLong)
        val a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(toRadians(currentLat)) * Math.cos(
                toRadians(lat)
            ) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val distanceInKm = earthRadius * c

        // Überprüfung, ob die Distanz innerhalb des angegebenen Radius liegt
        return distanceInKm <= distance
    }

    private fun calculateDistance(lat1: Double, long1: Double, lat2: Double, long2: Double): String {
        val earthRadius = 6371 // Durchmesser der Erde in Kilometern
        val decimalFormat = DecimalFormat("#.###")

        val distanceLat = toRadians(lat2 - lat1)
        val distanceLong = toRadians(long2 - long1)

        val a = sin(distanceLat / 2) * sin(distanceLat / 2) + cos(toRadians(lat1)) *
                cos(toRadians(lat2)) * sin(distanceLong / 2) * sin(distanceLong / 2)
        val b = 2 * atan2(sqrt(a), sqrt(1 - a))

        return decimalFormat.format(earthRadius * b)
    }
}

package de.hhn.softwarelabor.pawswipeapp

import java.lang.Math.*
import java.text.DecimalFormat
/**
 * The DistanceHelper class provides methods for calculating distances between geographical coordinates.
 */
class DistanceHelper {
    /**
     * Checks if a given geographical coordinate is within a specified range of another coordinate.
     *
     * @param currentLat  The latitude of the current coordinate.
     * @param currentLong The longitude of the current coordinate.
     * @param distance    The maximum distance in kilometers.
     * @param lat         The latitude of the target coordinate.
     * @param lon         The longitude of the target coordinate.
     * @return true if the target coordinate is within the specified range, false otherwise.
     */
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
    /**
     * Calculates the distance between two geographical coordinates.
     *
     * @param lat1  The latitude of the first coordinate.
     * @param long1 The longitude of the first coordinate.
     * @param lat2  The latitude of the second coordinate.
     * @param long2 The longitude of the second coordinate.
     * @return The distance between the two coordinates in kilometers, formatted as a string with three decimal places.
     */
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

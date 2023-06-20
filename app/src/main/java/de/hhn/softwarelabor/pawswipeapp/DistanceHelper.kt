package de.hhn.softwarelabor.pawswipeapp

class DistanceHelper {
    private fun getShelter(
        currentLat: Double,
        currentLong: Double,
        distance: Int,
        lat: Double,
        lon: Double
    ): Boolean {
        val earthRadius = 6371.0 // Radius der Erde in Kilometern
        val dLat = Math.toRadians(lat - currentLat)
        val dLon = Math.toRadians(lon - currentLong)
        val a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(currentLat)) * Math.cos(
                Math.toRadians(lat)
            ) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val distanceInKm = earthRadius * c

        // Überprüfung, ob die Distanz innerhalb des angegebenen Radius liegt
        return distanceInKm <= distance
    }
}

package de.hhn.softwarelabor.pawswipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.DecimalFormat
import kotlin.math.*


class DistanceCalc : AppCompatActivity() {

    private lateinit var  textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distance_calc)

        val lat1 = 49.1374813376093   // Breitengrad des ersten Ortes
        val lon1 = 9.219427654851538 // Längengrad des ersten Ortes
        val lat2 = 49.125650466628635  // Breitengrad des zweiten Ortes
        val lon2 = 9.216686440785807// Längengrad des zweiten Ortes

        val distance = calculateDistance(lat1, lon1, lat2, lon2)

        textView = findViewById(R.id.textView)
        textView.text = distance

    }

    private fun calculateDistance(lat1: Double, long1: Double, lat2: Double, long2: Double): String {
        val earthRadius = 6371 // Durchmesser der Erde in Kilometern
        val decimalFormat = DecimalFormat("#.###")

        val distanceLat = Math.toRadians(lat2 - lat1)
        val distanceLong = Math.toRadians(long2 - long1)

        val a = sin(distanceLat / 2) * sin(distanceLat / 2) + cos(Math.toRadians(lat1)) *
                cos(Math.toRadians(lat2)) * sin(distanceLong / 2) * sin(distanceLong / 2)
        val b = 2 * atan2(sqrt(a), sqrt(1 - a))

        return decimalFormat.format(earthRadius * b)
    }
}
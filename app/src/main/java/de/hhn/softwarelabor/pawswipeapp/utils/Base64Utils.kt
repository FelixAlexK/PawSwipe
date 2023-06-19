package de.hhn.softwarelabor.pawswipeapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

object Base64Utils {

    fun encode(bitmap: Bitmap): String {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imagesBytes = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(imagesBytes, Base64.DEFAULT)
    }

    fun decode(imageString: String): Bitmap {
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}
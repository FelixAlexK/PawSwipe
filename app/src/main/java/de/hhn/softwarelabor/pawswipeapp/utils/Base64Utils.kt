package de.hhn.softwarelabor.pawswipeapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * Base64Utils is a utility object for encoding and decoding images to and from Base64 strings.
 *
 * @author Felix  Kuhbier
 */
object Base64Utils {

    /**
     * Encodes a Bitmap image into a Base64 string.
     *
     * @param bitmap The Bitmap image to be encoded.
     * @return The Base64 encoded string representation of the input Bitmap image.
     */
    fun encode(bitmap: Bitmap): String {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
        val imagesBytes = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(imagesBytes, Base64.DEFAULT)
    }

    /**
     * Decodes a Base64 string back into a Bitmap image.
     *
     * @param imageString The Base64 encoded string representation of an image.
     * @return The decoded Bitmap image from the input Base64 string.
     */
    fun decode(imageString: String): Bitmap {
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}
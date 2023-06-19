package de.hhn.softwarelabor.pawswipeapp.utils

import android.graphics.Bitmap

/**
 * BitmapScaler is a utility object for scaling Bitmap images to fit specific dimensions.
 *
 * @author Felix Kuhbier
 */
object BitmapScaler {

    /**
     * Scales a Bitmap image to fit the specified width while maintaining the aspect ratio.
     *
     * @param b The Bitmap image to be scaled.
     * @param width The target width for the scaled image.
     * @return The scaled Bitmap image with the specified width and adjusted height to maintain the aspect ratio.
     */
    fun scaleToFitWidth(b: Bitmap, width: Int): Bitmap {
        val factor = width / b.width.toFloat()
        return Bitmap.createScaledBitmap(b, width, (b.height * factor).toInt(), true)
    }

    /**
     * Scales a Bitmap image to fit the specified height while maintaining the aspect ratio.
     *
     * @param b The Bitmap image to be scaled.
     * @param height The target height for the scaled image.
     * @return The scaled Bitmap image with the specified height and adjusted width to maintain the aspect ratio.
     */
    fun scaleToFitHeight(b: Bitmap, height: Int): Bitmap {
        val factor = height / b.height.toFloat()
        return Bitmap.createScaledBitmap(b, (b.width * factor).toInt(), height, true)
    }
}

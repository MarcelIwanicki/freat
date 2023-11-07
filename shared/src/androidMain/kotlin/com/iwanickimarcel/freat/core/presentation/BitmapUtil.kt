package com.iwanickimarcel.freat.core.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


@Composable
actual fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap? {
    return remember(bytes) {
        bytes?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size).asImageBitmap()
        }
    }
}

suspend fun ByteArray.scaleFitXY(width: Int, height: Int): ByteArray {
    return withContext(Dispatchers.Default) {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(this@scaleFitXY, 0, this@scaleFitXY.size, options)

        val originalWidth = options.outWidth
        val originalHeight = options.outHeight

        // Calculate the scaling factor for width and height
        val widthScaleFactor = width.toFloat() / originalWidth
        val heightScaleFactor = height.toFloat() / originalHeight

        // Use the smaller scaling factor to maintain aspect ratio
        val scaleFactor = minOf(widthScaleFactor, heightScaleFactor)

        // Calculate the new width and height
        val newWidth = (originalWidth * scaleFactor).toInt()
        val newHeight = (originalHeight * scaleFactor).toInt()

        // Create a new Bitmap with the calculated width and height
        options.inSampleSize = 1
        options.inJustDecodeBounds = false
        val originalBitmap =
            BitmapFactory.decodeByteArray(this@scaleFitXY, 0, this@scaleFitXY.size, options)
        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)

        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.toByteArray()
    }
}
package com.iwanickimarcel.core

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

        val widthScaleFactor = width.toFloat() / originalWidth
        val heightScaleFactor = height.toFloat() / originalHeight
        val scaleFactor = minOf(widthScaleFactor, heightScaleFactor)

        val newWidth = (originalWidth * scaleFactor).toInt()
        val newHeight = (originalHeight * scaleFactor).toInt()

        options.inSampleSize = 1
        options.inJustDecodeBounds = false
        val originalBitmap = BitmapFactory.decodeByteArray(
            this@scaleFitXY, 0, this@scaleFitXY.size, options
        )
        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)

        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.toByteArray()
    }
}
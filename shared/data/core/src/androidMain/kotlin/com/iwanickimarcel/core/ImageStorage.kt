package com.iwanickimarcel.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.UUID

actual class ImageStorage(
    private val context: Context
) {
    actual suspend fun saveImage(bytes: ByteArray): String {
        return withContext(Dispatchers.IO) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                val scaledBytes = bytes.scaleFitXY(
                    500, 500
                )
                it.write(scaledBytes)
            }
            fileName
        }
    }

    actual suspend fun getBytes(fileName: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            context.openFileInput(fileName).use {
                it.readBytes()
            }
        }
    }

    actual suspend fun deleteImage(fileName: String) {
        return withContext(Dispatchers.IO) {
            context.deleteFile(fileName)
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
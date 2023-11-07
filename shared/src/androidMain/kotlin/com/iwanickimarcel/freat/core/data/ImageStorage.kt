package com.iwanickimarcel.freat.core.data

import android.content.Context
import com.iwanickimarcel.freat.core.presentation.scaleFitXY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

actual class ImageStorage(
    private val context: Context
) {
    actual suspend fun saveImage(bytes: ByteArray): FileName {
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

    actual suspend fun getBytes(fileName: FileName): ByteArray? {
        return withContext(Dispatchers.IO) {
            context.openFileInput(fileName).use {
                it.readBytes()
            }
        }
    }

    actual suspend fun deleteImage(fileName: FileName) {
        return withContext(Dispatchers.IO) {
            context.deleteFile(fileName)
        }
    }

}
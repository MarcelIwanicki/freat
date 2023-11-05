package com.iwanickimarcel.freat.core.data

import android.content.Context
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
                it.write(bytes)
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
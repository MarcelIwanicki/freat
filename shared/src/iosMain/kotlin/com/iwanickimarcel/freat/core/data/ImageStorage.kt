package com.iwanickimarcel.freat.core.data

actual class ImageStorage {
    actual suspend fun saveImage(bytes: ByteArray): FileName {
        return ""
    }

    actual suspend fun getBytes(fileName: FileName): ByteArray? {
        return null
    }

    actual suspend fun deleteImage(fileName: FileName) {

    }

}
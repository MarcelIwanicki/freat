package com.iwanickimarcel.core

expect class ImageStorage {
    suspend fun saveImage(bytes: ByteArray): String
    suspend fun getBytes(fileName: String): ByteArray?
    suspend fun deleteImage(fileName: String)
}
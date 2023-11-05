package com.iwanickimarcel.freat.core.data

typealias FileName = String

expect class ImageStorage {
    suspend fun saveImage(bytes: ByteArray): FileName
    suspend fun getBytes(fileName: FileName): ByteArray?
    suspend fun deleteImage(fileName: FileName)
}
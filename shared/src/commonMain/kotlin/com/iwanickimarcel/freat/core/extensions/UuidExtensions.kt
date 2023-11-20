package com.iwanickimarcel.freat.core.extensions

import java.math.BigInteger
import java.security.MessageDigest
import java.util.UUID

fun generateUniqueId(): Long {
    val uuid = UUID.randomUUID()
    return uuid.leastSignificantBits xor uuid.mostSignificantBits
}

fun <T, V> combineIndexes(recipeIndex: T, productIndex: V): Long {
    val combinedString = "$recipeIndex-$productIndex"

    val bytes = MessageDigest.getInstance("SHA-256").digest(combinedString.toByteArray())
    val hashHex = bytes.joinToString("") { "%02x".format(it) }

    val hashBigInt = BigInteger(hashHex, 16)
    return hashBigInt.toLong()
}
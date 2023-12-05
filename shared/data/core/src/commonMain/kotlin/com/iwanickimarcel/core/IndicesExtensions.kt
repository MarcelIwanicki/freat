package com.iwanickimarcel.core

import java.math.BigInteger
import java.security.MessageDigest

fun <T, V> combineIndices(recipeIndex: T, productIndex: V): Long {
    val combinedString = "$recipeIndex-$productIndex"

    val bytes = MessageDigest.getInstance("SHA-256").digest(combinedString.toByteArray())
    val hashHex = bytes.joinToString("") { "%02x".format(it) }

    val hashBigInt = BigInteger(hashHex, 16)
    return hashBigInt.toLong()
}
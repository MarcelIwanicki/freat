package com.iwanickimarcel.freat.core.extensions

import java.util.UUID

fun generateUniqueId(): Long {
    val uuid = UUID.randomUUID()
    return uuid.leastSignificantBits xor uuid.mostSignificantBits
}
package com.iwanickimarcel.core

import java.util.UUID

actual fun generateUniqueId(): Long {
    val uuid = UUID.randomUUID()
    return uuid.leastSignificantBits xor uuid.mostSignificantBits
}
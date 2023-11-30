package com.iwanickimarcel.settings

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
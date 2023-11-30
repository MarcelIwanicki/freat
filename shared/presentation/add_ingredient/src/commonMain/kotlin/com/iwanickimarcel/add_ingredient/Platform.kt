package com.iwanickimarcel.add_ingredient

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
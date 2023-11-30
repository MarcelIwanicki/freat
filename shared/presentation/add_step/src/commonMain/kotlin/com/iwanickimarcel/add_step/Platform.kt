package com.iwanickimarcel.add_step

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package com.iwanickimarcel.home

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
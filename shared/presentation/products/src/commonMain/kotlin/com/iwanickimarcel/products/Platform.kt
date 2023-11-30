package com.iwanickimarcel.products

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
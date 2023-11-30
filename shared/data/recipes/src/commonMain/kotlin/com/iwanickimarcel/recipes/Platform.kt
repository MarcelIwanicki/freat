package com.iwanickimarcel.recipes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
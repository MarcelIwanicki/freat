package com.iwanickimarcel.recipes_search

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package com.iwanickimarcel.products_search

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
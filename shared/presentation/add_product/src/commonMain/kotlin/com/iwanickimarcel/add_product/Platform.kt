package com.iwanickimarcel.add_product

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
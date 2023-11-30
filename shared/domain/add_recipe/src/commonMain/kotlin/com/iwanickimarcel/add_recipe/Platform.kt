package com.iwanickimarcel.add_recipe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
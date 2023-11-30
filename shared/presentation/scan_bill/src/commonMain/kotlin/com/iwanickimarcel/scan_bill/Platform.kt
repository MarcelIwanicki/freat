package com.iwanickimarcel.scan_bill

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
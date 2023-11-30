package com.iwanickimarcel.products

data class Product (
    val name: String,
    val amount: Amount,
    val photoBytes: ByteArray?
)
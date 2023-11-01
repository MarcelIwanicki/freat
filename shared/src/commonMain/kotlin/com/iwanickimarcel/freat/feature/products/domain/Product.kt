package com.iwanickimarcel.freat.feature.products.domain

data class Product (
    val name: String,
    val amount: Amount,
    val photoBytes: ByteArray?
)
package com.iwanickimarcel.scan_bill

import com.iwanickimarcel.products.Product

data class ScanBillState(
    val photoBytes: ByteArray? = null,
    val products: List<Product> = emptyList(),
    val editProduct: Product? = null,
    val isAddProductOpen: Boolean = false,
    val success: Boolean = false,
)

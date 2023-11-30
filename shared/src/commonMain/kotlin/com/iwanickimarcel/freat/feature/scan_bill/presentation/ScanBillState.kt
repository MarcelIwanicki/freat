package com.iwanickimarcel.freat.feature.scan_bill.presentation

import com.iwanickimarcel.products.Product

data class ScanBillState(
    val photoBytes: ByteArray? = null,
    val products: List<Product> = emptyList(),
    val editProduct: Product? = null,
    val isAddProductOpen: Boolean = false,
    val success: Boolean = false,
)

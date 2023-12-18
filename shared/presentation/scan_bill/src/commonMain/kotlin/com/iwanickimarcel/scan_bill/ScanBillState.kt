package com.iwanickimarcel.scan_bill

import com.iwanickimarcel.products.Product

data class ScanBillState(
    val photoBytes: ByteArray? = null,
    val products: List<Product> = emptyList(),
    val editProduct: Product? = null,
    val isAddProductOpen: Boolean = false,
    val success: Boolean = false,
) {
    /*
        equals and hashcode have to be overwritten because of photoBytes ByteArray
    */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScanBillState

        if (photoBytes != null) {
            if (other.photoBytes == null) return false
            if (!photoBytes.contentEquals(other.photoBytes)) return false
        } else if (other.photoBytes != null) return false
        if (products != other.products) return false
        if (editProduct != other.editProduct) return false
        if (isAddProductOpen != other.isAddProductOpen) return false
        if (success != other.success) return false

        return true
    }

    override fun hashCode(): Int {
        var result = photoBytes?.contentHashCode() ?: 0
        result = 31 * result + products.hashCode()
        result = 31 * result + (editProduct?.hashCode() ?: 0)
        result = 31 * result + isAddProductOpen.hashCode()
        result = 31 * result + success.hashCode()
        return result
    }
}

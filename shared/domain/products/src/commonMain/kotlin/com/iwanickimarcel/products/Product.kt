package com.iwanickimarcel.products

data class Product(
    val name: String,
    val amount: Amount,
    val photoBytes: ByteArray?
) {
    /*
        equals and hashcode have to be overwritten because of photoBytes ByteArray
    */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (name != other.name) return false
        if (amount != other.amount) return false
        if (photoBytes != null) {
            if (other.photoBytes == null) return false
            if (!photoBytes.contentEquals(other.photoBytes)) return false
        } else if (other.photoBytes != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + amount.hashCode()
        result = 31 * result + (photoBytes?.contentHashCode() ?: 0)
        return result
    }
}
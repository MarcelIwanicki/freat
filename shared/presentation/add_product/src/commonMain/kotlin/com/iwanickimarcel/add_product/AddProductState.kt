package com.iwanickimarcel.add_product

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.products.AmountUnit

data class AddProductState(
    val name: TextFieldValue? = null,
    val nameError: String? = null,
    val amount: Double? = null,
    val amountError: String? = null,
    val amountUnit: AmountUnit = AmountUnit.MilliGram,
    val amountUnitMenuExpanded: Boolean = false,
    val photoBytes: ByteArray? = null,
    val success: Boolean = false
) {
    /*
        equals and hashcode have to be overwritten because of photoBytes ByteArray
    */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddProductState

        if (name != other.name) return false
        if (nameError != other.nameError) return false
        if (amount != other.amount) return false
        if (amountError != other.amountError) return false
        if (amountUnit != other.amountUnit) return false
        if (amountUnitMenuExpanded != other.amountUnitMenuExpanded) return false
        if (photoBytes != null) {
            if (other.photoBytes == null) return false
            if (!photoBytes.contentEquals(other.photoBytes)) return false
        } else if (other.photoBytes != null) return false
        if (success != other.success) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (nameError?.hashCode() ?: 0)
        result = 31 * result + (amount?.hashCode() ?: 0)
        result = 31 * result + (amountError?.hashCode() ?: 0)
        result = 31 * result + amountUnit.hashCode()
        result = 31 * result + amountUnitMenuExpanded.hashCode()
        result = 31 * result + (photoBytes?.contentHashCode() ?: 0)
        result = 31 * result + success.hashCode()
        return result
    }
}
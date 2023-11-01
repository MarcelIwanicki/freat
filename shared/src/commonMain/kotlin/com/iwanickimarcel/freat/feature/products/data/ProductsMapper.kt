package com.iwanickimarcel.freat.feature.products.data

import com.iwanickimarcel.freat.database.ProductEntity
import com.iwanickimarcel.freat.feature.products.domain.Amount
import com.iwanickimarcel.freat.feature.products.domain.AmountUnit
import com.iwanickimarcel.freat.feature.products.domain.Product

fun ProductEntity.toProduct(): Product {
    return Product(
        name = name,
        amount = Amount(
            amount = amount,
            unit = AmountUnit.values()[amountUnitId.toInt()]
        ),
        photoBytes = null
    )
}
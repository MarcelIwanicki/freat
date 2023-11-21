package com.iwanickimarcel.freat.feature.add_product.domain

import com.iwanickimarcel.freat.feature.products.domain.Amount
import com.iwanickimarcel.freat.feature.products.domain.AmountUnit
import com.iwanickimarcel.freat.feature.products.domain.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateProduct {
    suspend operator fun invoke(
        name: String?,
        amount: Double?,
        amountUnit: AmountUnit,
        photoBytes: ByteArray?,
        onProductAdded: suspend (Product) -> Unit,
        onSuccess: () -> Unit,
        onNameError: (result: ValidationResult) -> Unit,
        onAmountError: (result: ValidationResult) -> Unit,
    ) {
        val nameValidation = ProductValidator.validateName(name)
        val amountValidation = ProductValidator.validateAmount(amount)

        if (nameValidation == null && amountValidation == null) {
            withContext(Dispatchers.IO) {
                onProductAdded(
                    Product(
                        name = name ?: return@withContext,
                        amount = Amount(amount ?: return@withContext, amountUnit),
                        photoBytes = photoBytes,
                    )
                )
                onSuccess()
            }

            return
        }

        nameValidation?.let {
            onNameError(it)
        }

        amountValidation?.let {
            onAmountError(it)
        }
    }
}
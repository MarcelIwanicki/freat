package com.iwanickimarcel.freat.feature.add_product.presentation

import com.iwanickimarcel.freat.feature.add_product.domain.ProductValidator
import com.iwanickimarcel.freat.feature.products.domain.AmountUnit
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AddProductViewModel(
    private val productDataSource: ProductDataSource
) : ViewModel() {

    companion object {
        val AMOUNT_UNIT_OPTIONS = AmountUnit.values().map { it.abbreviation }
    }

    private val _state = MutableStateFlow(AddProductState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        AddProductState()
    )

    fun onEvent(event: AddProductEvent) {
        when (event) {
            is AddProductEvent.OnNameChanged -> {
                _state.value = _state.value.copy(
                    name = event.name,
                    nameError = null
                )
            }

            is AddProductEvent.OnAmountChanged -> {
                _state.value = _state.value.copy(
                    amount = event.amount.toDoubleOrNull() ?: return,
                    amountError = null
                )
            }

            is AddProductEvent.OnAmountUnitChanged -> {
                val unit = AmountUnit.values().find {
                    it.abbreviation == event.unit
                }

                unit?.let {
                    _state.value = _state.value.copy(
                        amountUnit = it,
                        amountUnitMenuExpanded = false
                    )
                }
            }

            is AddProductEvent.OnAmountUnitMenuStateChanged -> {
                val previousAmountUnitMenuExpanded = _state.value.amountUnitMenuExpanded

                _state.value = _state.value.copy(
                    amountUnitMenuExpanded = !previousAmountUnitMenuExpanded
                )
            }

            is AddProductEvent.OnAddProductClick -> {
                with(_state.value) {
                    val nameValidation = ProductValidator.validateName(name)
                    val amountValidation = ProductValidator.validateAmount(amount)

                    if (nameValidation == null && amountValidation == null) {
                        _state.value = _state.value.copy(
                            success = true
                        )
                        return@with
                    }

                    nameValidation?.let {
                        _state.value = _state.value.copy(
                            nameError = it
                        )
                    }

                    amountValidation?.let {
                        _state.value = _state.value.copy(
                            amountError = it
                        )
                    }

                }
            }
        }
    }
}
package com.iwanickimarcel.freat.feature.add_ingredient.presentation

import com.iwanickimarcel.freat.feature.add_product.domain.ProductValidator
import com.iwanickimarcel.freat.feature.products.domain.Amount
import com.iwanickimarcel.freat.feature.products.domain.AmountUnit
import com.iwanickimarcel.freat.feature.products.domain.Product
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddIngredientViewModel : ViewModel() {

    companion object {
        val AMOUNT_UNIT_OPTIONS = AmountUnit.values().map { it.abbreviation }
    }

    private val _state = MutableStateFlow(AddIngredientState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        AddIngredientState()
    )

    fun onEvent(event: AddIngredientEvent) {
        when (event) {
            is AddIngredientEvent.OnNameChanged -> {
                _state.value = _state.value.copy(
                    name = event.name,
                    nameError = null
                )
            }

            is AddIngredientEvent.OnAmountChanged -> {
                _state.value = _state.value.copy(
                    amount = event.amount.toDoubleOrNull() ?: return,
                    amountError = null
                )
            }

            is AddIngredientEvent.OnAmountUnitChanged -> {
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

            is AddIngredientEvent.OnAmountUnitMenuStateChanged -> {
                val previousAmountUnitMenuExpanded = _state.value.amountUnitMenuExpanded

                _state.value = _state.value.copy(
                    amountUnitMenuExpanded = !previousAmountUnitMenuExpanded
                )
            }

            is AddIngredientEvent.OnAddIngredientClick -> {
                with(_state.value) {
                    val nameValidation = ProductValidator.validateName(name)
                    val amountValidation = ProductValidator.validateAmount(amount)

                    if (nameValidation == null && amountValidation == null) {
                        viewModelScope.launch {
                            event.onIngredientAdded(
                                Product(
                                    name = name ?: return@launch,
                                    amount = Amount(amount ?: return@launch, amountUnit),
                                    photoBytes = null,
                                )
                            )
                            _state.value = _state.value.copy(
                                success = true
                            )
                        }

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
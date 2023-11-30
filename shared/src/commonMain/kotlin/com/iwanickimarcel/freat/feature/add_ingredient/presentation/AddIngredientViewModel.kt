package com.iwanickimarcel.freat.feature.add_ingredient.presentation

import com.iwanickimarcel.freat.feature.add_product.domain.ValidateProduct
import com.iwanickimarcel.products.AmountUnit
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class AddIngredientViewModel(
    private val validateProduct: ValidateProduct
) : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5.seconds
        val AMOUNT_UNIT_OPTIONS = AmountUnit.values().map { it.abbreviation }
    }

    private val _state = MutableStateFlow(AddIngredientState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(STOP_TIMEOUT),
        AddIngredientState()
    )

    fun onEvent(event: AddIngredientEvent) {
        when (event) {
            is AddIngredientEvent.OnEditProductProvided -> {
                _state.value = _state.value.copy(
                    name = event.product.name,
                    amount = event.product.amount.amount,
                    amountUnit = event.product.amount.unit
                )
            }

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

            is AddIngredientEvent.OnAddIngredientClick -> with(_state.value) {
                viewModelScope.launch {
                    validateProduct(
                        name = name,
                        amount = amount,
                        amountUnit = amountUnit,
                        photoBytes = null,
                        onProductAdded = event.onIngredientAdded,
                        onSuccess = {
                            _state.value = _state.value.copy(
                                success = true
                            )
                        },
                        onAmountError = {
                            _state.value = _state.value.copy(
                                amountError = it
                            )
                        },
                        onNameError = {
                            _state.value = _state.value.copy(
                                nameError = it
                            )
                        }
                    )
                }
            }
        }
    }
}
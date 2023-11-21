package com.iwanickimarcel.freat.feature.add_product.presentation

import com.iwanickimarcel.freat.feature.add_product.domain.ValidateProduct
import com.iwanickimarcel.freat.feature.products.domain.AmountUnit
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class AddProductViewModel(
    private val productDataSource: ProductDataSource,
    private val validateProduct: ValidateProduct
) : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5000.milliseconds
        val AMOUNT_UNIT_OPTIONS = AmountUnit.values().map { it.abbreviation }
    }

    private val _state = MutableStateFlow(AddProductState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(STOP_TIMEOUT),
        AddProductState()
    )

    fun onEvent(event: AddProductEvent) {
        when (event) {
            is AddProductEvent.OnEditProductProvided -> {
                viewModelScope.launch {
                    val product = productDataSource.getProductByName(event.name)
                    _state.value = _state.value.copy(
                        name = product.name,
                        amount = product.amount.amount,
                        amountUnit = product.amount.unit,
                        photoBytes = product.photoBytes
                    )
                }
            }

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

            is AddProductEvent.OnPhotoSelected -> {
                _state.value = _state.value.copy(
                    photoBytes = event.photoBytes
                )
            }

            is AddProductEvent.OnAddProductClick -> with(_state.value) {
                viewModelScope.launch {
                    validateProduct(
                        name = name,
                        amount = amount,
                        amountUnit = amountUnit,
                        photoBytes = photoBytes,
                        onProductAdded = productDataSource::insertProduct,
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
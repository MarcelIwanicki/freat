package com.iwanickimarcel.freat.feature.scan_bill.presentation

import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.scan_bill.ImageAnalyzer
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ScanBillViewModel(
    val imageAnalyzer: ImageAnalyzer,
    val productDataSource: ProductDataSource
) : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5.seconds
    }

    private val _state = MutableStateFlow(ScanBillState())
    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT), ScanBillState()
    )

    fun onEvent(event: ScanBillEvent) {
        when (event) {
            is ScanBillEvent.OnImageAnalysisRequested -> {
                viewModelScope.launch {
                    imageAnalyzer.getProductsFromImage(event.imageBitmap).collect {
                        _state.value = _state.value.copy(
                            products = it
                        )
                    }
                }
            }

            is ScanBillEvent.OnPhotoSelected -> {
                _state.value = _state.value.copy(
                    photoBytes = event.photoBytes
                )
            }

            is ScanBillEvent.OnAddProductPress -> {
                _state.value = _state.value.copy(
                    isAddProductOpen = true
                )
            }

            is ScanBillEvent.OnAddProductDismiss -> {
                _state.value = _state.value.copy(
                    isAddProductOpen = false
                )
            }

            is ScanBillEvent.OnProductAdded -> {
                _state.value = _state.value.copy(
                    products = _state.value.products.toMutableList().apply {
                        add(event.product)
                    }
                )
            }

            is ScanBillEvent.OnDeleteProductPress -> {
                _state.value = _state.value.copy(
                    products = _state.value.products.toMutableList().apply {
                        remove(event.product)
                    }
                )
            }

            is ScanBillEvent.OnEditProductPress -> {
                _state.value = _state.value.copy(
                    editProduct = event.product
                )
            }

            is ScanBillEvent.OnProductEdited -> {
                _state.value = _state.value.copy(
                    products = _state.value.products.toMutableList().apply {
                        remove(_state.value.products.find { it.name == event.product.name })
                        add(event.product)
                    }
                )
            }

            is ScanBillEvent.OnEditProductDismiss -> {
                _state.value = _state.value.copy(
                    editProduct = null
                )
            }

            is ScanBillEvent.OnAddProductsConfirm -> {
                viewModelScope.launch {
                    _state.value.products.forEach {
                        productDataSource.insertProduct(it)
                    }

                    _state.value = _state.value.copy(
                        success = true
                    )
                }
            }
        }
    }

}
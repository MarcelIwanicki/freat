package com.iwanickimarcel.freat.feature.products.presentation

import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ProductsViewModel(
    productDataSource: ProductDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state = combine(
        _state,
        productDataSource.getProducts()
    ) { state, products ->
        state.copy(
            products = products
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ProductsState())

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.OnProductLongPress -> {
                _state.value = _state.value.copy(
                    longPressedProduct = event.product
                )
            }

            is ProductsEvent.OnAddProductClick -> {
                _state.value = _state.value.copy(
                    addProductPressed = true
                )
            }

            else -> {

            }
        }
    }
}
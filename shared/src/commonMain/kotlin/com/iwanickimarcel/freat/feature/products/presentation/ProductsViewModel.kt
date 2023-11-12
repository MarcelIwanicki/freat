package com.iwanickimarcel.freat.feature.products.presentation

import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productDataSource: ProductDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state = combine(
        _state,
        productDataSource.getProducts()
    ) { state, products ->
        state.copy(
            products = products.filter {
                it.name.lowercase().contains(state.searchQuery.lowercase())
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ProductsState())

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.OnSearchQuery -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }

            is ProductsEvent.OnProductLongPress -> {
                _state.value = _state.value.copy(
                    longPressedProduct = event.product
                )
            }

            is ProductsEvent.OnProductLongPressDismiss -> {
                _state.value = _state.value.copy(
                    longPressedProduct = null
                )
            }

            is ProductsEvent.OnAddProductClick -> {
                _state.value = _state.value.copy(
                    addProductOpen = true
                )
            }

            is ProductsEvent.OnSearchBarClick -> {
                _state.value = _state.value.copy(
                    searchBarPressed = true
                )
            }

            is ProductsEvent.OnDeleteProductPress -> {
                _state.value = _state.value.copy(
                    productToDelete = event.product,
                    longPressedProduct = null
                )
            }

            is ProductsEvent.OnDeleteProductMenuDismiss -> {
                _state.value = _state.value.copy(
                    productToDelete = null
                )
            }

            is ProductsEvent.OnDeleteProductConfirm -> {
                val product = _state.value.productToDelete ?: return

                viewModelScope.launch {
                    productDataSource.deleteProduct(product.name)
                }

                _state.value = _state.value.copy(
                    productToDelete = null
                )
            }

            is ProductsEvent.OnEditProductPress -> {
                _state.value = _state.value.copy(
                    productToEdit = event.product,
                    longPressedProduct = null
                )
            }

            is ProductsEvent.OnAddProductDismiss -> {
                _state.value = _state.value.copy(
                    addProductOpen = false
                )
            }

            is ProductsEvent.OnEditProductDismiss -> {
                _state.value = _state.value.copy(
                    productToEdit = null
                )
            }
        }
    }
}
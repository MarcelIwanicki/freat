package com.iwanickimarcel.freat.feature.products_search.presentation

import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import com.iwanickimarcel.freat.feature.products_search.data.toProductsSearchHistoryItem
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryItem
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductsSearchViewModel(
    productDataSource: ProductDataSource,
    private val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsSearchState())
    val state = combine(
        _state,
        productsSearchHistoryDataSource.getLatestProductsSearchHistoryItems(4),
        productDataSource.getProducts()
    ) { state, historyItems, products ->

        val items = historyItems.filter {
            it.name.lowercase().contains(state.query.lowercase())
        } + products.map {
            it.toProductsSearchHistoryItem()
        }.filter { historyItem ->
            historyItem.name.lowercase().contains(state.query.lowercase()) && !historyItems.any {
                it.name == historyItem.name
            }
        }.take(10)

        state.copy(
            items = items
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ProductsSearchState())

    fun onEvent(event: ProductsSearchEvent) {
        when (event) {
            is ProductsSearchEvent.OnQueryChange -> {
                _state.value = _state.value.copy(
                    query = event.query
                )
            }

            is ProductsSearchEvent.OnItemPress -> {
                viewModelScope.launch {
                    productsSearchHistoryDataSource.insertProductsSearchHistoryItem(
                        item = ProductsSearchHistoryItem(
                            name = event.itemName,
                            type = ProductsSearchHistoryItem.Type.History
                        )
                    )
                }

                _state.value = _state.value.copy(
                    itemPressedName = event.itemName
                )
            }

            is ProductsSearchEvent.OnSearchConfirm -> {
                _state.value.query.takeIf { it.isNotEmpty() }?.let {
                    viewModelScope.launch {
                        productsSearchHistoryDataSource.insertProductsSearchHistoryItem(
                            item = ProductsSearchHistoryItem(
                                name = it,
                                type = ProductsSearchHistoryItem.Type.History
                            )
                        )
                    }
                }


                _state.value = _state.value.copy(
                    isSearchConfirmed = true
                )
            }

            is ProductsSearchEvent.OnBackPressed -> {
                _state.value = _state.value.copy(
                    isBackPressed = true
                )
            }
        }
    }
}
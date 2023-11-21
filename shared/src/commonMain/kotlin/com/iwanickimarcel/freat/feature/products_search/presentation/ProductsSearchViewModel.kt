package com.iwanickimarcel.freat.feature.products_search.presentation

import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import com.iwanickimarcel.freat.feature.products_search.domain.FilterProductsSearchHistoryItems
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryItem
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class ProductsSearchViewModel(
    productDataSource: ProductDataSource,
    filterProductsSearchHistoryItems: FilterProductsSearchHistoryItems,
    private val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource,
) : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5000.milliseconds
    }

    private val _state = MutableStateFlow(ProductsSearchState())
    val state = combine(
        _state,
        productsSearchHistoryDataSource.getLatestProductsSearchHistoryItems(4),
        productDataSource.getProducts()
    ) { state, historyItems, products ->

        val items = filterProductsSearchHistoryItems(
            items = historyItems,
            products = products,
            query = state.query
        )

        state.copy(items = items)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT), ProductsSearchState())

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
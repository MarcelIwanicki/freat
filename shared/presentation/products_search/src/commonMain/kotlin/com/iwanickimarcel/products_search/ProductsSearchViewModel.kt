package com.iwanickimarcel.products_search

import com.iwanickimarcel.products.ProductDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ProductsSearchViewModel(
    productDataSource: ProductDataSource,
    filterProductsSearchHistoryItems: FilterProductsSearchHistoryItems,
    private val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource,
) : ViewModel() {

    companion object {
        private const val SEARCH_HISTORY_ITEMS_LIMIT = 4
        private val STOP_TIMEOUT = 5.seconds
    }

    private val _state = MutableStateFlow(ProductsSearchState())
    val state = combine(
        _state,
        productsSearchHistoryDataSource.getLatestProductsSearchHistoryItems(
            SEARCH_HISTORY_ITEMS_LIMIT
        ),
        productDataSource.getProducts()
    ) { state, historyItems, products ->

        state.copy(
            items = filterProductsSearchHistoryItems(
                items = historyItems,
                products = products,
                query = state.query.text
            )
        )
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
                _state.value.query.takeIf { it.text.isNotEmpty() }?.let {
                    viewModelScope.launch {
                        productsSearchHistoryDataSource.insertProductsSearchHistoryItem(
                            item = ProductsSearchHistoryItem(
                                name = it.text,
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
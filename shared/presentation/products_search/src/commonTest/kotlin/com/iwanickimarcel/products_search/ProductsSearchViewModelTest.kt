package com.iwanickimarcel.products_search

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.products.g
import com.iwanickimarcel.test.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductsSearchViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var productDataSource: ProductDataSource
    private lateinit var filterProductsSearchHistoryItems: FilterProductsSearchHistoryItems
    private lateinit var productsSearchHistoryDataSource: ProductsSearchHistoryDataSource
    private lateinit var viewModel: ProductsSearchViewModel

    @Before
    fun setUp() {
        productDataSource = FakeProductDataSource()
        filterProductsSearchHistoryItems = FilterProductsSearchHistoryItems()
        productsSearchHistoryDataSource = FakeProductsSearchHistoryDataSource()
        viewModel = ProductsSearchViewModel(
            productDataSource = productDataSource,
            filterProductsSearchHistoryItems = filterProductsSearchHistoryItems,
            productsSearchHistoryDataSource = productsSearchHistoryDataSource
        )
    }

    @Test
    fun `initially state with search item should be emitted`() = runTest {
        val products = listOf(
            Product(
                name = "Apple",
                amount = 10.g,
                photoBytes = null
            ),
            Product(
                name = "Banana",
                amount = 10.g,
                photoBytes = null
            ),
            Product(
                name = "Pork",
                amount = 20.g,
                photoBytes = null
            ),
        )

        val historyItems = listOf(
            ProductsSearchHistoryItem(
                name = "Lorem ipsum",
                type = ProductsSearchHistoryItem.Type.History
            ),
            ProductsSearchHistoryItem(
                name = "Apple",
                type = ProductsSearchHistoryItem.Type.History
            ),
            ProductsSearchHistoryItem(
                name = "Banana",
                type = ProductsSearchHistoryItem.Type.History
            ),
            ProductsSearchHistoryItem(
                name = "Strawberry",
                type = ProductsSearchHistoryItem.Type.History
            ),
            ProductsSearchHistoryItem(
                name = "Raspberry",
                type = ProductsSearchHistoryItem.Type.History
            ),
        )

        products.forEach {
            productDataSource.insertProduct(it)
        }

        historyItems.forEach {
            productsSearchHistoryDataSource.insertProductsSearchHistoryItem(it)
        }

        viewModel.state.test {
            val expectedState = ProductsSearchState(
                items = listOf(
                    ProductsSearchHistoryItem(
                        name = "Lorem ipsum",
                        type = ProductsSearchHistoryItem.Type.History
                    ),
                    ProductsSearchHistoryItem(
                        name = "Apple",
                        type = ProductsSearchHistoryItem.Type.History
                    ),
                    ProductsSearchHistoryItem(
                        name = "Banana",
                        type = ProductsSearchHistoryItem.Type.History
                    ),
                    ProductsSearchHistoryItem(
                        name = "Strawberry",
                        type = ProductsSearchHistoryItem.Type.History
                    ),
                    ProductsSearchHistoryItem(
                        name = "Raspberry",
                        type = ProductsSearchHistoryItem.Type.History
                    ),
                    ProductsSearchHistoryItem(
                        name = "Pork",
                        type = ProductsSearchHistoryItem.Type.Search
                    ),
                )
            )

            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on query change event called, state with new query should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(
                ProductsSearchEvent.OnQueryChange(TextFieldValue("Banana"))
            )

            val expectedState = ProductsSearchState(
                query = TextFieldValue("Banana")
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on item press event called, state with new set of items and item pressed name should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    ProductsSearchEvent.OnItemPress("Banana")
                )

                val expectedState = ProductsSearchState(
                    items = listOf(
                        ProductsSearchHistoryItem(
                            name = "Banana",
                            type = ProductsSearchHistoryItem.Type.History
                        )
                    ),
                    itemPressedName = "Banana"
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on search confirm event called, state with new set of items and search confirm should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    ProductsSearchEvent.OnQueryChange(TextFieldValue("Banana"))
                )
                viewModel.onEvent(
                    ProductsSearchEvent.OnSearchConfirm
                )

                val expectedState = ProductsSearchState(
                    items = listOf(
                        ProductsSearchHistoryItem(
                            name = "Banana",
                            type = ProductsSearchHistoryItem.Type.History
                        )
                    ),
                    query = TextFieldValue("Banana"),
                    isSearchConfirmed = true
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on back pressed event called, state with back pressed should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(
                ProductsSearchEvent.OnBackPressed
            )

            val expectedState = ProductsSearchState(
                isBackPressed = true
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
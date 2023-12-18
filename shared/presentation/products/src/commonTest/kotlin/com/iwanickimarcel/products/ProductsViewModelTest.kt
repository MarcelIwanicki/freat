package com.iwanickimarcel.products

import app.cash.turbine.test
import com.iwanickimarcel.test.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductsViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var productDataSource: ProductDataSource
    private lateinit var filterProductsByQuery: FilterProductsByQuery
    private lateinit var viewModel: ProductsViewModel

    @Before
    fun setUp() {
        productDataSource = FakeProductDataSource()
        filterProductsByQuery = FilterProductsByQuery()
        viewModel = ProductsViewModel(
            productDataSource = productDataSource,
            filterProductsByQuery = filterProductsByQuery
        )
    }

    @Test
    fun `initially state with products from dataSource should be emitted`() = runTest {
        val products = listOf(
            Product(
                name = "Apple",
                amount = 10.mg,
                photoBytes = null
            ),
            Product(
                name = "Banana",
                amount = 20.mg,
                photoBytes = null
            ),
            Product(
                name = "Potato",
                amount = 40.g,
                photoBytes = null
            ),
        )

        products.forEach {
            productDataSource.insertProduct(it)
        }

        viewModel.state.test {
            val expectedState = ProductsState(
                products = products
            )

            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when search query event is called, state with products from dataSource filtered by query should be emitted`() =
        runTest {
            val products = listOf(
                Product(
                    name = "Apple",
                    amount = 10.mg,
                    photoBytes = null
                ),
                Product(
                    name = "Banana",
                    amount = 20.mg,
                    photoBytes = null
                ),
                Product(
                    name = "Apple pie",
                    amount = 200.mg,
                    photoBytes = null
                ),
                Product(
                    name = "Potato",
                    amount = 40.g,
                    photoBytes = null
                ),
            )

            products.forEach {
                productDataSource.insertProduct(it)
            }

            viewModel.state.test {
                viewModel.onEvent(ProductsEvent.OnSearchQuery("appl"))

                val expectedState = ProductsState(
                    searchQuery = "appl",
                    products = listOf(
                        Product(
                            name = "Apple",
                            amount = 10.mg,
                            photoBytes = null
                        ),
                        Product(
                            name = "Apple pie",
                            amount = 200.mg,
                            photoBytes = null
                        ),
                    )
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when long press event is called, state with long pressed product should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    ProductsEvent.OnProductLongPress(
                        Product(
                            name = "Apple",
                            amount = 10.g,
                            photoBytes = null
                        )
                    )
                )

                val expectedState = ProductsState(
                    longPressedProduct = Product(
                        name = "Apple",
                        amount = 10.g,
                        photoBytes = null
                    )
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when long press dismiss event is called, state with null long pressed product should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    ProductsEvent.OnProductLongPress(
                        Product(
                            name = "Apple",
                            amount = 10.g,
                            photoBytes = null
                        )
                    )
                )

                viewModel.onEvent(ProductsEvent.OnProductLongPressDismiss)

                val expectedState = ProductsState(
                    longPressedProduct = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when add product click event is called, state with add product open should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(ProductsEvent.OnAddProductClick)

                val expectedState = ProductsState(
                    addProductOpen = true
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when search bar click event is called, state with search bar pressed should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(ProductsEvent.OnSearchBarClick)

                val expectedState = ProductsState(
                    searchBarPressed = true
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when delete product press event is called, state with product to delete should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    ProductsEvent.OnDeleteProductPress(
                        Product(
                            name = "Apple",
                            amount = 10.g,
                            photoBytes = null
                        )
                    )
                )

                val expectedState = ProductsState(
                    productToDelete = Product(
                        name = "Apple",
                        amount = 10.g,
                        photoBytes = null
                    ),
                    longPressedProduct = null
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when delete product menu dismiss event is called, state with null product to delete should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    ProductsEvent.OnDeleteProductPress(
                        Product(
                            name = "Apple",
                            amount = 10.g,
                            photoBytes = null
                        )
                    )
                )

                viewModel.onEvent(ProductsEvent.OnDeleteProductMenuDismiss)

                val expectedState = ProductsState(
                    productToDelete = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when delete product confirm event is called, product should be deleted from data source`() =
        runTest {
            val products = listOf(
                Product(
                    name = "Apple",
                    amount = 10.mg,
                    photoBytes = null
                ),
                Product(
                    name = "Banana",
                    amount = 20.mg,
                    photoBytes = null
                ),
                Product(
                    name = "Potato",
                    amount = 40.g,
                    photoBytes = null
                ),
            )

            products.forEach {
                productDataSource.insertProduct(it)
            }

            productDataSource.getProducts().test {
                viewModel.onEvent(
                    ProductsEvent.OnDeleteProductPress(
                        Product(
                            name = "Apple",
                            amount = 10.g,
                            photoBytes = null
                        )
                    )
                )

                viewModel.onEvent(ProductsEvent.OnDeleteProductConfirm)

                val expectedProducts = listOf(
                    Product(
                        name = "Banana",
                        amount = 20.mg,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 40.g,
                        photoBytes = null
                    ),
                )

                assertThat(awaitItem()).isEqualTo(expectedProducts)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when delete product confirm event is called, state with null product to delete should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    ProductsEvent.OnDeleteProductPress(
                        Product(
                            name = "Apple",
                            amount = 10.g,
                            photoBytes = null
                        )
                    )
                )

                viewModel.onEvent(ProductsEvent.OnDeleteProductConfirm)

                val expectedState = ProductsState(
                    productToDelete = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when edit product press is called, state with product to edit should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    ProductsEvent.OnEditProductPress(
                        Product(
                            name = "Apple",
                            amount = 10.g,
                            photoBytes = null
                        )
                    )
                )

                val expectedState = ProductsState(
                    productToEdit = Product(
                        name = "Apple",
                        amount = 10.g,
                        photoBytes = null
                    ),
                    longPressedProduct = null
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when add product dismiss event is called, state with false add product open should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(ProductsEvent.OnAddProductClick)
                viewModel.onEvent(ProductsEvent.OnAddProductDismiss)

                val expectedState = ProductsState(
                    addProductOpen = false
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when edit product dismiss event is called, state with null product to edit should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    ProductsEvent.OnEditProductPress(
                        Product(
                            name = "Apple",
                            amount = 10.g,
                            photoBytes = null
                        )
                    )
                )

                viewModel.onEvent(ProductsEvent.OnEditProductDismiss)

                val expectedState = ProductsState(
                    productToEdit = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when scan bill event is called, state with null product to edit should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(ProductsEvent.OnScanBillClick)

                val expectedState = ProductsState(
                    isScanBillClicked = true
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

}
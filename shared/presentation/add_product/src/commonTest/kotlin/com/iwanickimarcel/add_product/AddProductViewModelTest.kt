package com.iwanickimarcel.add_product

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.iwanickimarcel.products.AmountUnit
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.products.g
import com.iwanickimarcel.products.l
import com.iwanickimarcel.products.mg
import com.iwanickimarcel.test.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddProductViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var productDataSource: ProductDataSource
    private lateinit var validateProduct: ValidateProduct
    private lateinit var viewModel: AddProductViewModel

    @Before
    fun setUp() {
        productDataSource = FakeProductDataSource()
        validateProduct = ValidateProduct()
        viewModel = AddProductViewModel(
            productDataSource = productDataSource,
            validateProduct = validateProduct
        )
    }

    @Test
    fun `initially state with default values should be emitted`() = runTest {
        viewModel.state.test {
            val expectedState = AddProductState()

            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on edit product provided event called, state with product found should be emitted`() =
        runTest {
            productDataSource.insertProduct(
                product = Product(
                    name = "Apple",
                    amount = 10.mg,
                    photoBytes = null
                )
            )
            productDataSource.insertProduct(
                product = Product(
                    name = "Water",
                    amount = 20.l,
                    photoBytes = null
                )
            )
            productDataSource.insertProduct(
                product = Product(
                    name = "Banana",
                    amount = 30.g,
                    photoBytes = null
                )
            )

            viewModel.state.test {
                viewModel.onEvent(AddProductEvent.OnEditProductProvided("Water"))

                val expectedState = AddProductState(
                    name = TextFieldValue("Water"),
                    amount = 20.0,
                    amountUnit = AmountUnit.Liter,
                    photoBytes = null
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on name changed event called, state with name should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(AddProductEvent.OnNameChanged(TextFieldValue("Apple")))

            val expectedState = AddProductState(
                name = TextFieldValue("Apple"),
                nameError = null
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on amount changed event called, state with amount should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(AddProductEvent.OnAmountChanged("30"))

            val expectedState = AddProductState(
                amount = 30.0,
                amountError = null
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on amount unit changed event called, state with amount unit should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(AddProductEvent.OnAmountUnitChanged("kg"))

                val expectedState = AddProductState(
                    amountUnit = AmountUnit.KiloGram,
                    amountUnitMenuExpanded = false
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on amount unit menu state changed event called, state with negated amount unit menu state should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(AddProductEvent.OnAmountUnitMenuStateChanged)

                val expectedState = AddProductState(
                    amountUnitMenuExpanded = true
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on amount unit menu state changed event called twice, state with negated amount unit menu state should be emitted`() =
        runTest {
            viewModel.state.test {
                repeat(2) {
                    viewModel.onEvent(AddProductEvent.OnAmountUnitMenuStateChanged)
                }

                val expectedState = AddProductState(
                    amountUnitMenuExpanded = false
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on photo selected event called, state with photo bytes should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddProductEvent.OnPhotoSelected(
                        photoBytes = byteArrayOf(1, 2, 3, 4, 5)
                    )
                )

                val expectedState = AddProductState(
                    photoBytes = byteArrayOf(1, 2, 3, 4, 5)
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on add product click event called and data is correct, state with success should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(AddProductEvent.OnNameChanged(TextFieldValue("Apple")))
                viewModel.onEvent(AddProductEvent.OnAmountChanged("30"))
                viewModel.onEvent(AddProductEvent.OnAmountUnitChanged("g"))
                viewModel.onEvent(AddProductEvent.OnPhotoSelected(byteArrayOf(1, 2, 3, 4, 5)))
                viewModel.onEvent(AddProductEvent.OnAddProductClick)

                val expectedState = AddProductState(
                    name = TextFieldValue("Apple"),
                    amount = 30.0,
                    amountUnit = AmountUnit.Gram,
                    photoBytes = byteArrayOf(1, 2, 3, 4, 5),
                    success = true
                )

                skipItems(5)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on add product click event called and name is incorrect, state with name error should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(AddProductEvent.OnNameChanged(TextFieldValue("")))
                viewModel.onEvent(AddProductEvent.OnAmountChanged("30"))
                viewModel.onEvent(AddProductEvent.OnAmountUnitChanged("g"))
                viewModel.onEvent(AddProductEvent.OnPhotoSelected(byteArrayOf(1, 2, 3, 4, 5)))
                viewModel.onEvent(AddProductEvent.OnAddProductClick)


                skipItems(5)
                assertThat(awaitItem().nameError).isNotEmpty
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `when on add product click event called and amount is incorrect, state with amount error should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(AddProductEvent.OnNameChanged(TextFieldValue("Apple")))
                viewModel.onEvent(AddProductEvent.OnAmountUnitChanged("g"))
                viewModel.onEvent(AddProductEvent.OnPhotoSelected(byteArrayOf(1, 2, 3, 4, 5)))
                viewModel.onEvent(AddProductEvent.OnAddProductClick)


                skipItems(4)
                assertThat(awaitItem().amountError).isNotEmpty
                cancelAndIgnoreRemainingEvents()
            }
        }

}
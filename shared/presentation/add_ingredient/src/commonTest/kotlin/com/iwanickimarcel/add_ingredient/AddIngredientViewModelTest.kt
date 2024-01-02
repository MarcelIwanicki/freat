package com.iwanickimarcel.add_ingredient

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.iwanickimarcel.add_product.ValidateProduct
import com.iwanickimarcel.products.AmountUnit
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.products.mg
import com.iwanickimarcel.test.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddIngredientViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var validateProduct: ValidateProduct
    private lateinit var viewModel: AddIngredientViewModel

    @Before
    fun setUp() {
        validateProduct = ValidateProduct()
        viewModel = AddIngredientViewModel(
            validateProduct = validateProduct
        )
    }

    @Test
    fun `initially state with default values should be emitted`() = runTest {
        viewModel.state.test {
            val expectedState = AddIngredientState()
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when edit product provided event is called, state with ingredient info should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddIngredientEvent.OnEditProductProvided(
                        Product(
                            name = "Apple",
                            amount = 10.mg,
                            photoBytes = null
                        )
                    )
                )

                val expectedState = AddIngredientState(
                    name = TextFieldValue("Apple"),
                    amount = 10.0,
                    amountUnit = AmountUnit.MilliGram
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when name changed event is called, state with name should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(
                AddIngredientEvent.OnNameChanged(TextFieldValue("Banana"))
            )

            val expectedState = AddIngredientState(
                name = TextFieldValue("Banana"),
                nameError = null
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when amount changed event is called, state with amount should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(
                AddIngredientEvent.OnAmountChanged("20")
            )

            val expectedState = AddIngredientState(
                amount = 20.0,
                amountError = null
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when amount unit changed event is called, state with amount unit should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddIngredientEvent.OnAmountUnitChanged("ml")
                )

                val expectedState = AddIngredientState(
                    amountUnit = AmountUnit.MilliLiter,
                    amountUnitMenuExpanded = false
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when amount unit menu state changed event is called, state with negated value should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(AddIngredientEvent.OnAmountUnitMenuStateChanged)

                val expectedState = AddIngredientState(
                    amountUnitMenuExpanded = true
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when amount unit menu state changed twice event is called, state with twice negated value should be emitted`() =
        runTest {
            viewModel.state.test {
                repeat(2) {
                    viewModel.onEvent(AddIngredientEvent.OnAmountUnitMenuStateChanged)
                }

                val expectedState = AddIngredientState(
                    amountUnitMenuExpanded = false
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `when add ingredient click event is called and name is wrong, state with name error should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddIngredientEvent.OnNameChanged(TextFieldValue(""))
                )
                viewModel.onEvent(
                    AddIngredientEvent.OnAddIngredientClick {}
                )

                skipItems(2)
                assertThat(awaitItem().nameError).isNotEmpty
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when add ingredient click event is called and amount is wrong, state with amount error should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddIngredientEvent.OnAddIngredientClick {}
                )

                skipItems(1)
                assertThat(awaitItem().amountError).isNotEmpty
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when add ingredient click event is called with correct inputs, state with success should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddIngredientEvent.OnNameChanged(TextFieldValue("Banana"))
                )

                viewModel.onEvent(
                    AddIngredientEvent.OnAmountChanged("20")
                )

                viewModel.onEvent(
                    AddIngredientEvent.OnAmountUnitChanged("g")
                )

                viewModel.onEvent(
                    AddIngredientEvent.OnAddIngredientClick {}
                )

                val expectedState = AddIngredientState(
                    name = TextFieldValue("Banana"),
                    amount = 20.0,
                    amountUnit = AmountUnit.Gram,
                    success = true
                )

                skipItems(4)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }
}
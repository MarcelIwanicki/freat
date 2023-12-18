package com.iwanickimarcel.add_product

import app.cash.turbine.test
import com.iwanickimarcel.products.ProductDataSource
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
}
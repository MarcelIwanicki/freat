package com.iwanickimarcel.products

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.iwanickimarcel.add_product.AddProductViewModel
import com.iwanickimarcel.add_product.ValidateProduct
import com.iwanickimarcel.core.ImagePickerFactory
import com.iwanickimarcel.freat.navigation.ProductsNavigationBarFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        val productDataSource: ProductDataSource = FakeProductDataSource()
        val filterProductsByQuery = FilterProductsByQuery()
        val validateProduct = ValidateProduct()

        val productsViewModel = ProductsViewModel(
            productDataSource = productDataSource,
            filterProductsByQuery = filterProductsByQuery
        )
        val addProductViewModel = AddProductViewModel(
            productDataSource = productDataSource,
            validateProduct = validateProduct
        )

        composeRule.setContent {
            ProductsScreen(
                getViewModel = {
                    productsViewModel
                },
                getAddProductViewModel = {
                    addProductViewModel
                },
                imagePicker = ImagePickerFactory().createPicker(),
                navigateToScanBill = {

                },
                navigateToProductsSearch = {

                },
                navigationBarFactory = ProductsNavigationBarFactory(),
                searchQuery = ""
            )
        }
    }

    @Test
    fun screen_contains_addProduct_button() {
        composeRule.onNode(hasText("Add product")).assertIsDisplayed()
    }
}
package com.iwanickimarcel.freat

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import com.iwanickimarcel.freat.android.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductsScreenTest {

    companion object {
        private const val TEST_TAG_ADD_PRODUCT = "add_product"
        private const val TEST_TAG_SCAN_THE_BILL = "scan_the_bill"

        private const val TEST_TAG_ADD_PRODUCT_NAME = "add_product_name"
        private const val TEST_TAG_ADD_PRODUCT_AMOUNT = "add_product_amount"
        private const val TEST_TAG_CONFIRM_ADD_PRODUCT = "confirm_add_product"

        private const val TEST_TAG_ADD_INGREDIENT_PLUS = "add_ingredient_plus"
        private const val TEST_TAG_ADD_INGREDIENT_NAME = "add_ingredient_name"
        private const val TEST_TAG_ADD_INGREDIENT_AMOUNT = "add_ingredient_amount"
        private const val TEST_TAG_CONFIRM_ADD_INGREDIENT = "confirm_add_ingredient"
        private const val TEST_TAG_ADD_INGREDIENT_CONFIRM_ADDING_PRODUCTS =
            "add_ingredient_confirm_adding_products"
    }

    @get:Rule
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp() {
        composeRule.onNode(hasText("Products")).performClick()
    }

    @Test
    fun test_screen_contains_addProduct_button() {
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT)).assertIsDisplayed()
    }

    @Test
    fun test_screen_contains_scanTheBill_button() {
        composeRule.onNode(hasTestTag(TEST_TAG_SCAN_THE_BILL)).assertIsDisplayed()
    }

    @Test
    fun test_adding_new_product() {
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT)).performClick()

        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT_NAME)).performTextReplacement("Apple")
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT_AMOUNT)).performTextReplacement("20.0")
        composeRule.onNode(hasTestTag(TEST_TAG_CONFIRM_ADD_PRODUCT)).performClick()

        composeRule.onNode(hasText("Apple")).assertIsDisplayed()
    }

    @Test
    fun test_adding_new_products_from_scan_the_bill() {
        composeRule.onNode(hasTestTag(TEST_TAG_SCAN_THE_BILL)).performClick()

        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_PLUS)).performClick()
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_NAME))
            .performTextReplacement("Banana")
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_AMOUNT))
            .performTextReplacement("20.0")
        composeRule.onNode(hasTestTag(TEST_TAG_CONFIRM_ADD_INGREDIENT)).performClick()

        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_PLUS)).performClick()
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_NAME))
            .performTextReplacement("Mustard")
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_AMOUNT))
            .performTextReplacement("555.0")
        composeRule.onNode(hasTestTag(TEST_TAG_CONFIRM_ADD_INGREDIENT)).performClick()

        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_PLUS)).performClick()
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_NAME))
            .performTextReplacement("Strawberry")
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_AMOUNT)).performTextReplacement("1.0")
        composeRule.onNode(hasTestTag(TEST_TAG_CONFIRM_ADD_INGREDIENT)).performClick()

        composeRule.onNode(hasTestTag(TEST_TAG_ADD_INGREDIENT_CONFIRM_ADDING_PRODUCTS))
            .performClick()

        composeRule.onNode(hasText("Banana")).assertIsDisplayed()
        composeRule.onNode(hasText("Mustard")).assertIsDisplayed()
        composeRule.onNode(hasText("Strawberry")).assertIsDisplayed()
    }

    @Test
    fun test_editing_product() {
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT)).performClick()

        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT_NAME)).performTextReplacement("Steak")
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT_AMOUNT)).performTextReplacement("20.0")
        composeRule.onNode(hasTestTag(TEST_TAG_CONFIRM_ADD_PRODUCT)).performClick()

        composeRule.onNode(hasText("Steak")).performTouchInput { longClick() }
        composeRule.onNode(hasText("Edit Steak")).performClick()

        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT_NAME)).performTextReplacement("NewSteak")
        composeRule.onNode(hasTestTag(TEST_TAG_CONFIRM_ADD_PRODUCT)).performClick()

        composeRule.onNode(hasText("NewSteak")).assertIsDisplayed()
    }

    @Test
    fun test_deleting_product() {
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT)).performClick()

        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT_NAME)).performTextReplacement("Lemon")
        composeRule.onNode(hasTestTag(TEST_TAG_ADD_PRODUCT_AMOUNT)).performTextReplacement("20.0")
        composeRule.onNode(hasTestTag(TEST_TAG_CONFIRM_ADD_PRODUCT)).performClick()

        composeRule.onNode(hasText("Lemon")).assertIsDisplayed()

//        composeRule.onNode(hasText("Lemon")).performTouchInput { longClick() }
//        composeRule.onNode(hasText("Delete Lemon")).performClick()
//
//        composeRule.onNode(hasText("Delete")).performClick()
//        composeRule.onNode(hasText("Lemon")).assertIsNotDisplayed()
    }
}
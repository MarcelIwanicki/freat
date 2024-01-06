package com.iwanickimarcel.freat

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.test.espresso.Espresso.closeSoftKeyboard
import com.iwanickimarcel.freat.android.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductsScreenTest {

    private object TestTags {
        const val ADD_INGREDIENT_PLUS = "add_ingredient_plus"
        const val PRODUCTS_GRID = "products_grid"
    }

    @get:Rule
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp() {
        composeRule.onNodeWithText("Products").performClick()
    }

    @Test
    fun test_screen_contains_addProduct_button() {
        composeRule.onNodeWithText("Add product").assertIsDisplayed()
    }

    @Test
    fun test_screen_contains_scanTheBill_button() {
        composeRule.onNodeWithText("Scan the bill").assertIsDisplayed()
    }

    @Test
    fun test_adding_new_product() {
        with(composeRule) {
            onNodeWithText("Add product").performClick()

            onNodeWithText("Name").performTextInput("Apple")
            closeSoftKeyboard()
            onNodeWithText("Amount").performTextInput("20.0")
            closeSoftKeyboard()
            onNodeWithText("Confirm adding product").performClick()

            onNodeWithTag(TestTags.PRODUCTS_GRID).performScrollToNode(hasText("Apple"))
            onNodeWithText("Apple").assertIsDisplayed()
        }
    }

    @Test
    fun test_adding_new_products_from_scan_the_bill() {
        with(composeRule) {
            onNodeWithText("Scan the bill").performClick()

            onNodeWithTag(TestTags.ADD_INGREDIENT_PLUS).performClick()
            onNodeWithText("Name").performTextInput("Banana")
            closeSoftKeyboard()
            onNodeWithText("Amount").performTextInput("20.0")
            closeSoftKeyboard()
            onNodeWithText("Confirm adding ingredient").performClick()

            onNodeWithTag(TestTags.ADD_INGREDIENT_PLUS).performClick()
            onNodeWithText("Name").performTextInput("Mustard")
            closeSoftKeyboard()
            onNodeWithText("Amount").performTextInput("10.0")
            closeSoftKeyboard()
            onNodeWithText("Confirm adding ingredient").performClick()

            onNodeWithTag(TestTags.ADD_INGREDIENT_PLUS).performClick()
            onNodeWithText("Name").performTextInput("Strawberry")
            closeSoftKeyboard()
            onNodeWithText("Amount").performTextInput("1.0")
            closeSoftKeyboard()
            onNodeWithText("Confirm adding ingredient").performClick()

            onNodeWithText("Confirm adding products").performClick()

            onNodeWithTag(TestTags.PRODUCTS_GRID).performScrollToNode(hasText("Banana"))
            onNodeWithText("Banana").assertIsDisplayed()
            onNodeWithTag(TestTags.PRODUCTS_GRID).performScrollToNode(hasText("Mustard"))
            onNodeWithText("Mustard").assertIsDisplayed()
            onNodeWithTag(TestTags.PRODUCTS_GRID).performScrollToNode(hasText("Strawberry"))
            onNodeWithText("Strawberry").assertIsDisplayed()
        }
    }

    @Test
    fun test_editing_product() {
        with(composeRule) {
            onNodeWithText("Add product").performClick()

            onNodeWithText("Name").performTextInput("Steak")
            closeSoftKeyboard()
            onNodeWithText("Amount").performTextInput("20.0")
            closeSoftKeyboard()
            onNodeWithText("Confirm adding product").performClick()

            onNodeWithTag(TestTags.PRODUCTS_GRID).performScrollToNode(hasText("Steak"))
            onNodeWithText("Steak").performTouchInput { longClick() }
            onNodeWithText("Edit Steak").performClick()

            onNodeWithText("Name").performTextInput("New")
            closeSoftKeyboard()
            onNodeWithText("Save changes").performClick()

            onNodeWithTag(TestTags.PRODUCTS_GRID).performScrollToNode(hasText("NewSteak"))
            onNodeWithText("NewSteak").assertIsDisplayed()
        }
    }

    @Test
    fun test_deleting_product() {
        with(composeRule) {
            onNodeWithText("Add product").performClick()

            onNodeWithText("Name").performTextInput("Lemon")
            closeSoftKeyboard()
            onNodeWithText("Amount").performTextInput("20.0")
            closeSoftKeyboard()
            onNodeWithText("Confirm adding product").performClick()

            onNodeWithTag(TestTags.PRODUCTS_GRID).performScrollToNode(hasText("Lemon"))
            onNodeWithText("Lemon").performTouchInput { longClick() }
            onNodeWithText("Delete Lemon").performClick()

            onNodeWithText("Delete").performClick()
        }
    }
}
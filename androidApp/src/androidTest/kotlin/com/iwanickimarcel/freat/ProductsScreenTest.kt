package com.iwanickimarcel.freat

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.iwanickimarcel.freat.android.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductsScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp() {
        composeRule.onNode(hasText("Products")).performClick()
    }

    @Test
    fun screen_contains_addProduct_button() {
        composeRule.onNode(hasText("Add product")).assertIsDisplayed()
    }
}
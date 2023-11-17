package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe

sealed interface AddRecipeEvent {
    data class OnEditRecipeProvided(val id: Long) : AddRecipeEvent
    data class OnNameChanged(val name: String) : AddRecipeEvent
    data class OnPhotoSelected(val photoBytes: ByteArray) : AddRecipeEvent
    object OnAddIngredientPress : AddRecipeEvent
    object OnAddIngredientDismiss : AddRecipeEvent
    data class OnIngredientAdded(val ingredient: Product) : AddRecipeEvent
    object OnAddStepPress : AddRecipeEvent
    object OnAddStepDismiss : AddRecipeEvent
    data class OnStepAdded(val step: Recipe.Step) : AddRecipeEvent
    data class OnTagAdded(val tagName: String) : AddRecipeEvent
    data class OnTagRemoved(val index: Int) : AddRecipeEvent
    data class OnTagTextFieldValueChanged(val textFieldValue: TextFieldValue) : AddRecipeEvent
    object OnAddRecipeConfirm : AddRecipeEvent
}
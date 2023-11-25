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
    data class OnEditIngredientPress(val product: Product) : AddRecipeEvent
    data class OnDeleteIngredientPress(val product: Product) : AddRecipeEvent
    object OnEditIngredientDismiss : AddRecipeEvent
    data class OnIngredientAdded(val ingredient: Product) : AddRecipeEvent
    data class OnIngredientEdited(val ingredient: Product) : AddRecipeEvent
    object OnAddStepPress : AddRecipeEvent
    data class OnEditStepPress(val step: Recipe.Step) : AddRecipeEvent
    data class OnDeleteStepPress(val step: Recipe.Step) : AddRecipeEvent
    object OnAddStepDismiss : AddRecipeEvent
    data class OnStepAdded(val step: Recipe.Step) : AddRecipeEvent
    data class OnTagAdded(val tagName: String) : AddRecipeEvent
    data class OnTagRemoved(val index: Int) : AddRecipeEvent
    data class OnTagTextFieldValueChanged(val textFieldValue: TextFieldValue) : AddRecipeEvent
    object OnAddRecipeConfirm : AddRecipeEvent
}
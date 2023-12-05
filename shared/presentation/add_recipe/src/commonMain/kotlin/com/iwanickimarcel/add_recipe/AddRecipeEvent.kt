package com.iwanickimarcel.add_recipe

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.recipes.Recipe

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
    object OnEditStepDismiss : AddRecipeEvent
    data class OnStepAdded(val step: Recipe.Step) : AddRecipeEvent
    data class OnStepEdited(val step: Recipe.Step) : AddRecipeEvent
    data class OnTagAdded(val tagName: String) : AddRecipeEvent
    data class OnTagRemoved(val index: Int) : AddRecipeEvent
    data class OnTagTextFieldValueChanged(val textFieldValue: TextFieldValue) : AddRecipeEvent
    object OnAddRecipeConfirm : AddRecipeEvent
}
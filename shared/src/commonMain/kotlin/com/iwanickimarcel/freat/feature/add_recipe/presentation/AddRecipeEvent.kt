package com.iwanickimarcel.freat.feature.add_recipe.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product

sealed interface AddRecipeEvent {
    data class OnNameChanged(val name: String) : AddRecipeEvent
    data class OnPhotoSelected(val photoBytes: ByteArray) : AddRecipeEvent
    object OnAddIngredientPress : AddRecipeEvent
    object OnAddIngredientDismiss : AddRecipeEvent
    data class OnAddIngredientAdded(val ingredient: Product) : AddRecipeEvent
}
package com.iwanickimarcel.freat.feature.recipes.presentation

import com.iwanickimarcel.freat.feature.recipes.domain.Recipe

sealed interface RecipesEvent {
    data class OnRecipeLongPress(val recipe: Recipe) : RecipesEvent
    object OnRecipeLongPressDismiss : RecipesEvent
    data class OnDeleteRecipePress(val recipe: Recipe) : RecipesEvent
    object OnDeleteRecipeMenuDismiss : RecipesEvent
    object OnDeleteRecipeConfirm : RecipesEvent
    data class OnEditRecipePress(val recipe: Recipe) : RecipesEvent
}
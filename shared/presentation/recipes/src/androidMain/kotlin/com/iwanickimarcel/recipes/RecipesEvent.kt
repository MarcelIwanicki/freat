package com.iwanickimarcel.recipes

sealed interface RecipesEvent {
    data class OnSearchQuery(val query: String) : RecipesEvent
    object OnSearchBarClick : RecipesEvent
    data class OnRecipeLongPress(val recipe: Recipe) : RecipesEvent
    object OnRecipeLongPressDismiss : RecipesEvent
    data class OnDeleteRecipePress(val recipe: Recipe) : RecipesEvent
    object OnDeleteRecipeMenuDismiss : RecipesEvent
    object OnDeleteRecipeConfirm : RecipesEvent
    data class OnEditRecipePress(val recipe: Recipe) : RecipesEvent
    data class OnFavoriteClick(val recipe: Recipe) : RecipesEvent
}
package com.iwanickimarcel.freat.feature.recipes.domain

class FilterRecipesByQuery {
    operator fun invoke(recipes: List<Recipe>, searchQuery: String) = recipes.filter {
        it.name.lowercase().contains(searchQuery.lowercase())
    }
}
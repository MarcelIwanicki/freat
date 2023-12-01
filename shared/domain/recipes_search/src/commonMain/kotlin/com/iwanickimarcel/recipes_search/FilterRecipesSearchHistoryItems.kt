package com.iwanickimarcel.recipes_search

import com.iwanickimarcel.recipes.Recipe

class FilterRecipesSearchHistoryItems {
    operator fun invoke(
        items: List<RecipesSearchHistoryItem>,
        recipes: List<Recipe>,
        query: String
    ): List<RecipesSearchHistoryItem> = items.filter {
        it.query.lowercase().contains(query.lowercase())
    } + recipes.map {
        it.toRecipesSearchHistoryItem()
    }.filter { historyItem ->
        historyItem.query.lowercase().contains(query.lowercase()) && !items.any {
            it.query == historyItem.query
        }
    }.take(10)
}
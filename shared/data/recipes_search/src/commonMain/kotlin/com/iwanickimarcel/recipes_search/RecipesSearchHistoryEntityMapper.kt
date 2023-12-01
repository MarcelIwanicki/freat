package com.iwanickimarcel.recipes_search

import com.iwanickimarcel.recipessearch.database.RecipesSearchHistoryEntity

fun RecipesSearchHistoryEntity.toRecipesSearchHistoryItem(): RecipesSearchHistoryItem {
    return RecipesSearchHistoryItem(
        query = query,
        type = RecipesSearchHistoryItem.Type.History
    )
}
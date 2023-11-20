package com.iwanickimarcel.freat.feature.recipes_search.data

import com.iwanickimarcel.freat.feature.recipes_search.domain.RecipesSearchHistoryItem
import com.iwanickimarcel.freat.recipessearchhistorydatabase.RecipesSearchHistoryEntity

fun RecipesSearchHistoryEntity.toRecipesSearchHistoryItem(): RecipesSearchHistoryItem {
    return RecipesSearchHistoryItem(
        query = query,
        type = RecipesSearchHistoryItem.Type.History
    )
}
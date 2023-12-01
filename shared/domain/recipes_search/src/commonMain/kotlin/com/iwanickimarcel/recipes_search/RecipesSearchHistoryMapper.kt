package com.iwanickimarcel.recipes_search

import com.iwanickimarcel.recipes.Recipe

fun Recipe.toRecipesSearchHistoryItem(): RecipesSearchHistoryItem {
    return RecipesSearchHistoryItem(
        query = name,
        type = RecipesSearchHistoryItem.Type.Search
    )
}
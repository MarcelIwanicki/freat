package com.iwanickimarcel.recipes_search

import kotlinx.coroutines.flow.Flow

interface RecipesSearchHistoryDataSource {
    fun getLatestRecipesSearchHistoryItems(limit: Int): Flow<List<RecipesSearchHistoryItem>>
    suspend fun insertRecipesSearchHistoryItem(item: RecipesSearchHistoryItem)
}
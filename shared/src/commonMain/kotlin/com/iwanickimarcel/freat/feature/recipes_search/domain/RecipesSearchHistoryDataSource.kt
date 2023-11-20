package com.iwanickimarcel.freat.feature.recipes_search.domain

import kotlinx.coroutines.flow.Flow

interface RecipesSearchHistoryDataSource {
    fun getLatestRecipesSearchHistoryItems(limit: Int): Flow<List<RecipesSearchHistoryItem>>
    suspend fun insertRecipesSearchHistoryItem(item: RecipesSearchHistoryItem)
}
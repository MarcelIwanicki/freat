package com.iwanickimarcel.recipes_search

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.iwanickimarcel.recipes_search.database.RecipesSearchHistoryDatabase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightRecipesSearchHistoryDataSource(
    database: RecipesSearchHistoryDatabase
) : RecipesSearchHistoryDataSource {

    private val queries = database.recipesSearchHistoryQueries

    override fun getLatestRecipesSearchHistoryItems(
        limit: Int
    ): Flow<List<RecipesSearchHistoryItem>> = flow {
        val recipesSearchHistoryItems = queries
            .getLatestRecipesSearchHistoryEntities(limit.toLong())
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { entities ->
                entities.map {
                    it.toRecipesSearchHistoryItem()
                }
            }

        emitAll(recipesSearchHistoryItems)
    }

    override suspend fun insertRecipesSearchHistoryItem(item: RecipesSearchHistoryItem) {
        queries.insertRecipesSearchHistoryEntity(
            query = item.query,
            createdAt = Clock.System.now().toEpochMilliseconds()
        )
    }
}
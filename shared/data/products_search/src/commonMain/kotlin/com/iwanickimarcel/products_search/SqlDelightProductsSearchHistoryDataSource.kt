package com.iwanickimarcel.products_search

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.iwanickimarcel.products_search.database.ProductsSearchHistoryDatabase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightProductsSearchHistoryDataSource(
    database: ProductsSearchHistoryDatabase
) : ProductsSearchHistoryDataSource {

    private val queries = database.productsSearchHistoryQueries

    override fun getLatestProductsSearchHistoryItems(
        limit: Int
    ): Flow<List<ProductsSearchHistoryItem>> = flow {
        val productsSearchHistoryItems = queries
            .getLatestProductsSearchHistoryEntities(limit.toLong())
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { entities ->
                entities.map {
                    it.toProductsSearchHistoryItem()
                }
            }

        emitAll(productsSearchHistoryItems)
    }

    override suspend fun insertProductsSearchHistoryItem(item: ProductsSearchHistoryItem) {
        queries.insertProductsSearchHistoryEntity(
            name = item.name,
            createdAt = Clock.System.now().toEpochMilliseconds()
        )
    }
}
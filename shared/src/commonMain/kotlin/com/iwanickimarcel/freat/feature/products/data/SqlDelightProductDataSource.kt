package com.iwanickimarcel.freat.feature.products.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.iwanickimarcel.freat.database.ProductsDatabase
import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock


class SqlDelightProductDataSource(
    database: ProductsDatabase
) : ProductDataSource {

    private val queries = database.productQueries

    override fun getProducts(): Flow<List<Product>> = flow {
        val products = queries
            .getProducts()
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { entities ->
                entities.map {
                    it.toProduct()
                }
            }

        emitAll(products)
    }

    override suspend fun insertProduct(product: Product) {
        queries.insertProcuct(
            name = product.name,
            amount = product.amount.amount,
            amountUnitId = product.amount.unit.ordinal.toLong(),
            createdAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = null,
        )
    }

    override suspend fun deleteProduct(name: String) {
        queries.deleteProcuct(name)
    }


}
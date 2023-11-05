package com.iwanickimarcel.freat.feature.products.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.iwanickimarcel.freat.core.data.ImageStorage
import com.iwanickimarcel.freat.database.ProductsDatabase
import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock


class SqlDelightProductDataSource(
    database: ProductsDatabase,
    private val imageStorage: ImageStorage
) : ProductDataSource {

    private val queries = database.productQueries

    override fun getProducts(): Flow<List<Product>> = flow {
        val products = queries
            .getProducts()
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { entities ->
                supervisorScope {
                    entities.map {
                        async {
                            it.toProduct(imageStorage)
                        }
                    }.map { it.await() }
                }
            }

        emitAll(products)
    }

    override suspend fun insertProduct(product: Product) {
        val imagePath = product.photoBytes?.let {
            imageStorage.saveImage(it)
        }

        queries.insertProcuct(
            name = product.name,
            amount = product.amount.amount,
            amountUnitId = product.amount.unit.ordinal.toLong(),
            createdAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = imagePath,
        )
    }

    override suspend fun deleteProduct(name: String) {
        val entity = queries.getProductByName(name).executeAsOne()
        entity.imagePath?.let {
            imageStorage.deleteImage(it)
        }
        queries.deleteProcuct(name)
    }


}
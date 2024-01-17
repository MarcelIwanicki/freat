package com.iwanickimarcel.products_search

import com.iwanickimarcel.products.Product
import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.products.g
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeProductDataSource : ProductDataSource {

    private val fakeProducts = mutableListOf<Product>()

    override fun getProducts(): Flow<List<Product>> {
        return flowOf(fakeProducts)
    }

    override suspend fun getProductByName(name: String): Product {
        return fakeProducts.find {
            it.name == name
        } ?: Product(
            name = "",
            amount = 0.g,
            photoBytes = null
        )
    }

    override suspend fun insertProduct(product: Product) {
        fakeProducts.removeIf {
            it.name == product.name
        }
        fakeProducts.add(product)
    }

    override suspend fun deleteProduct(name: String) {
        fakeProducts.remove(
            fakeProducts.find {
                it.name == name
            }
        )
    }
}
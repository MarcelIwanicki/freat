package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource

expect class ProductsModule {
    val productDataSource: ProductDataSource
}
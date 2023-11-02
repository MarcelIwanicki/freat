package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource

expect class AppModule {
    val productDataSource: ProductDataSource
}
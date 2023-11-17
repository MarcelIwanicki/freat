package com.iwanickimarcel.freat.di

import android.content.Context

actual class AppModule(
    private val context: Context
) {
    actual val imageModule: ImageModule by lazy {
        ImageModule(context)
    }

    actual val databaseModule: DatabaseModule by lazy {
        DatabaseModule(context)
    }

    actual val dataSourceModule: DataSourceModule by lazy {
        DataSourceModule(
            productsDatabase = databaseModule.productsDatabase,
            productsSearchHistoryDatabase = databaseModule.productsSearchHistoryDatabase,
            recipesDatabase = databaseModule.recipesDatabase,
            imageStorage = imageModule.imageStorage
        )
    }

    actual val viewModelModule: ViewModelModule by lazy {
        ViewModelModule(
            productDataSource = dataSourceModule.productDataSource,
            productsSearchHistoryDataSource = dataSourceModule.productsSearchHistoryDataSource,
            recipeDataSource = dataSourceModule.recipeDataSource
        )
    }
}
package com.iwanickimarcel.freat.di

import android.content.Context
import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.core.data.ImageStorage
import com.iwanickimarcel.freat.core.data.ProductsDatabaseDriverFactory
import com.iwanickimarcel.freat.core.data.ProductsSearchHistoryDatabaseDriverFactory
import com.iwanickimarcel.freat.core.data.RecipesDatabaseDriverFactory
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.core.presentation.ImagePickerFactory
import com.iwanickimarcel.freat.feature.products.data.SqlDelightProductDataSource
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import com.iwanickimarcel.freat.feature.products_search.data.SqlDelightProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.recipes.data.SqlDelightRecipeDataSource
import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import com.iwanickimarcel.freat.products_database.ProductsDatabase
import com.iwanickimarcel.freat.products_search_history_database.ProductsSearchHistoryDatabase
import com.iwanickimarcel.freat.recipes_database.RecipesDatabase

actual class AppModule(
    private val context: Context
) {
    private val imageStorage by lazy {
        ImageStorage(context)
    }

    private val productsDatabase by lazy {
        ProductsDatabase(
            driver = ProductsDatabaseDriverFactory(context).create()
        )
    }

    private val productsSearchHistoryDatabase by lazy {
        ProductsSearchHistoryDatabase(
            driver = ProductsSearchHistoryDatabaseDriverFactory(context).create()
        )
    }

    private val recipesDatabase by lazy {
        RecipesDatabase(
            driver = RecipesDatabaseDriverFactory(context).create()
        )
    }

    actual val productDataSource: ProductDataSource by lazy {
        SqlDelightProductDataSource(
            database = productsDatabase,
            imageStorage = imageStorage
        )
    }

    actual val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource by lazy {
        SqlDelightProductsSearchHistoryDataSource(
            database = productsSearchHistoryDatabase
        )
    }

    actual val recipeDataSource: RecipeDataSource by lazy {
        SqlDelightRecipeDataSource(
            recipesDatabase = recipesDatabase,
            productsDatabase = productsDatabase,
            imageStorage = imageStorage
        )
    }

    actual val imagePicker: ImagePicker
        @Composable
        get() {
            return ImagePickerFactory().createPicker()
        }
}
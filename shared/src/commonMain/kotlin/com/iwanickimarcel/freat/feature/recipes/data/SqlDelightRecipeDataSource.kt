package com.iwanickimarcel.freat.feature.recipes.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.iwanickimarcel.freat.core.data.ImageStorage
import com.iwanickimarcel.freat.core.extensions.flattenToList
import com.iwanickimarcel.freat.feature.products.data.toProduct
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import com.iwanickimarcel.freat.products_database.ProductsDatabase
import com.iwanickimarcel.freat.recipes_database.RecipesDatabase
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock

class SqlDelightRecipeDataSource(
    recipesDatabase: RecipesDatabase,
    productsDatabase: ProductsDatabase,
    private val imageStorage: ImageStorage
) : RecipeDataSource {

    private val recipeQueries = recipesDatabase.recipeQueries
    private val productQueries = productsDatabase.productQueries

    override fun getRecipes(): Flow<List<Recipe>> = flow {
        val recipes = recipeQueries
            .getRecipes()
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { recipeEntities ->
                supervisorScope {
                    recipeEntities.map { recipeEntity ->
                        async {
                            val recipeProductsEntities = recipeQueries
                                .getRecipeProducts(recipeEntity.id)
                                .asFlow()
                                .mapToList(currentCoroutineContext())
                                .flattenToList()

                            val products = recipeProductsEntities
                                .map { productQueries.getProductByName(it.productName) }
                                .asFlow()
                                .mapToList(currentCoroutineContext())
                                .flattenToList()
                                .map { it.toProduct(imageStorage) }

                            val recipeTagsEntities = recipeQueries
                                .getRecipeTags(recipeEntity.id)
                                .asFlow()
                                .mapToList(currentCoroutineContext())
                                .flattenToList()

                            val tags = recipeTagsEntities
                                .map { recipeQueries.getTagByName(it.tagName) }
                                .asFlow()
                                .mapToList(currentCoroutineContext())
                                .flattenToList()
                                .map { it.toTag() }

                            recipeEntity.toRecipe(
                                imageStorage = imageStorage,
                                products = products,
                                tags = tags
                            )
                        }
                    }.map { it.await() }
                }
            }

        emitAll(recipes)
    }

    override suspend fun getRecipeById(id: Long): Recipe {
        val recipeEntity = recipeQueries
            .getRecipeById(id)
            .executeAsOne()

        val recipeProductsEntities = recipeQueries
            .getRecipeProducts(id)
            .asFlow()
            .mapToList(currentCoroutineContext())
            .flattenToList()

        val products = recipeProductsEntities
            .map { productQueries.getProductByName(it.productName) }
            .asFlow()
            .mapToList(currentCoroutineContext())
            .flattenToList()
            .map { it.toProduct(imageStorage) }

        val recipeTagsEntities = recipeQueries
            .getRecipeTags(id)
            .asFlow()
            .mapToList(currentCoroutineContext())
            .flattenToList()

        val tags = recipeTagsEntities
            .map { recipeQueries.getTagByName(it.tagName) }
            .asFlow()
            .mapToList(currentCoroutineContext())
            .flattenToList()
            .map { it.toTag() }

        return recipeEntity.toRecipe(
            imageStorage = imageStorage,
            products = products,
            tags = tags
        )
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        val imagePath = recipe.photoBytes?.let {
            imageStorage.saveImage(it)
        }

        val allProducts = productQueries
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
            }.flattenToList()

        val allTags = recipeQueries
            .getTags()
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { entities ->
                supervisorScope {
                    entities.map {
                        async {
                            it.toTag()
                        }
                    }
                }.map { it.await() }
            }.flattenToList()

        val productsToAdd = recipe.products.filterNot {
            allProducts.contains(it)
        }

        val tagsToAdd = recipe.tags.filterNot {
            allTags.contains(it)
        }

        productsToAdd.forEach {
            productQueries.insertProcuct(
                name = it.name,
                amount = it.amount.amount,
                amountUnitId = it.amount.unit.ordinal.toLong(),
                createdAt = Clock.System.now().toEpochMilliseconds(),
                imagePath = imagePath,
            )
        }

        tagsToAdd.forEach {
            recipeQueries.insertTag(it.name)
        }

        recipeQueries.insertRecipe(
            id = recipe.id,
            name = recipe.name,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = imagePath
        )
    }

    override suspend fun deleteRecipe(id: Long) {
        val entity = recipeQueries.getRecipeById(id).executeAsOne()
        entity.imagePath?.let {
            imageStorage.deleteImage(it)
        }

        recipeQueries.deleteAllRecipeTag(id)
        recipeQueries.deleteAllRecipeProducts(id)
        recipeQueries.deleteRecipe(id)
    }
}
package com.iwanickimarcel.freat.feature.recipes.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.iwanickimarcel.freat.core.data.ImageStorage
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import com.iwanickimarcel.freat.recipes_database.RecipesDatabase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightRecipeDataSource(
    database: RecipesDatabase,
    private val imageStorage: ImageStorage
) : RecipeDataSource {

    private val recipeQueries = database.recipeQueries

    override fun getRecipes(): Flow<List<Recipe>> = flow {
        val recipes = recipeQueries
            .getRecipes()
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { recipeEntities ->
                recipeEntities.map { recipeEntity ->
                    val products = recipeQueries
                        .getRecipeProducts(recipeEntity.id)
                        .asFlow()
                        .mapToList(currentCoroutineContext())
                        .map { recipeProductsEntity ->
                            recipeProductsEntity.map {
                                it.toProduct()
                            }
                        }

                    val tags = recipeQueries
                        .getRecipeTags(recipeEntity.id)
                        .asFlow()
                        .mapToList(currentCoroutineContext())
                        .map { recipeTagsEntity ->
                            recipeTagsEntity.map {
                                it.toTag()
                            }
                        }

                    val steps = recipeQueries
                        .getRecipeSteps(recipeEntity.id)
                        .asFlow()
                        .mapToList(currentCoroutineContext())
                        .map { recipeStepEntity ->
                            recipeStepEntity.map {
                                it.toStep()
                            }
                        }

                    recipeEntity.toRecipe(
                        imageStorage = imageStorage,
                        products = products.first(),
                        tags = tags.first(),
                        steps = steps.first()
                    )
                }
            }

        emitAll(recipes)
    }


    override suspend fun getRecipeById(id: Long): Recipe {
        val recipeEntity = recipeQueries
            .getRecipeById(id)
            .executeAsOne()

        val products = recipeQueries
            .getRecipeProducts(recipeEntity.id)
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { recipeProductsEntity ->
                recipeProductsEntity.map {
                    it.toProduct()
                }
            }

        val tags = recipeQueries
            .getRecipeTags(recipeEntity.id)
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { recipeTagsEntity ->
                recipeTagsEntity.map {
                    it.toTag()
                }
            }

        val steps = recipeQueries
            .getRecipeSteps(recipeEntity.id)
            .asFlow()
            .mapToList(currentCoroutineContext())
            .map { recipeStepEntity ->
                recipeStepEntity.map {
                    it.toStep()
                }
            }


        return recipeEntity.toRecipe(
            imageStorage = imageStorage,
            products = products.first(),
            tags = tags.first(),
            steps = steps.first()
        )
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        val imagePath = recipe.photoBytes?.let {
            imageStorage.saveImage(it)
        }

        recipe.products.forEach {
            recipeQueries.insertRecipeProduct(
                id = null,
                recipeId = recipe.id,
                productName = it.name,
                productAmount = it.amount.amount,
                productAmountUnitId = it.amount.unit.ordinal.toLong()
            )
        }

        recipe.tags.forEach {
            recipeQueries.insertTag(it.name)
            recipeQueries.insertRecipeTag(
                id = null,
                recipeId = recipe.id,
                tagName = it.name
            )
        }

        recipe.steps.forEach {
            recipeQueries.insertRecipeStep(
                recipeId = recipe.id,
                step = it.step.toLong(),
                description = it.description
            )
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
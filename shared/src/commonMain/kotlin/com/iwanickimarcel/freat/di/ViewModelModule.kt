package com.iwanickimarcel.freat.di

import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.feature.add_ingredient.presentation.AddIngredientViewModel
import com.iwanickimarcel.freat.feature.add_product.presentation.AddProductViewModel
import com.iwanickimarcel.freat.feature.add_recipe.presentation.AddRecipeViewModel
import com.iwanickimarcel.freat.feature.add_step.presentation.AddStepViewModel
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import com.iwanickimarcel.freat.feature.products.presentation.ProductsViewModel
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.products_search.presentation.ProductsSearchViewModel
import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import com.iwanickimarcel.freat.feature.recipes.presentation.RecipesViewModel
import com.iwanickimarcel.freat.feature.recipes_search.domain.RecipesSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.recipes_search.presentation.RecipesSearchViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

class ViewModelModule(
    private val productDataSource: ProductDataSource,
    private val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource,
    private val recipeDataSource: RecipeDataSource,
    private val recipesSearchHistoryDataSource: RecipesSearchHistoryDataSource,
) {
    private val useCaseModule: UseCaseModule = UseCaseModule()

    val productsViewModel: ProductsViewModel
        @Composable
        get() = getViewModel(
            key = "products-screen",
            factory = viewModelFactory {
                ProductsViewModel(
                    productDataSource = productDataSource,
                    filterProductsByQuery = useCaseModule.filterProductsByQuery
                )
            }
        )

    val addProductViewModel: AddProductViewModel
        @Composable
        get() = getViewModel(
            key = "add-product-screen",
            factory = viewModelFactory {
                AddProductViewModel(
                    productDataSource = productDataSource,
                    validateProduct = useCaseModule.validateProduct
                )
            }
        )

    val productsSearchViewModel: ProductsSearchViewModel
        @Composable
        get() = getViewModel(
            key = "products-search-screen",
            factory = viewModelFactory {
                ProductsSearchViewModel(
                    productDataSource = productDataSource,
                    productsSearchHistoryDataSource = productsSearchHistoryDataSource,
                    filterProductsSearchHistoryItems = useCaseModule.filterProductsSearchHistoryItems
                )
            }
        )

    val recipesViewModel: RecipesViewModel
        @Composable
        get() = getViewModel(
            key = "recipes-search-screen",
            factory = viewModelFactory {
                RecipesViewModel(
                    recipeDataSource = recipeDataSource,
                    productDataSource = productDataSource,
                    filterRecipesByQuery = useCaseModule.filterRecipesByQuery,
                    getRecipesWithOwnedProductsPercent = useCaseModule.getRecipesWithOwnedProductsPercent
                )
            }
        )

    val addRecipeViewModel: AddRecipeViewModel
        @Composable
        get() = getViewModel(
            key = "add-recipe-screen",
            factory = viewModelFactory {
                AddRecipeViewModel(
                    recipeDataSource = recipeDataSource,
                    validateRecipe = useCaseModule.validateRecipe
                )
            }
        )

    val recipesSearchViewModel: RecipesSearchViewModel
        @Composable
        get() = getViewModel(
            key = "recipes-search-screen",
            factory = viewModelFactory {
                RecipesSearchViewModel(
                    recipeDataSource = recipeDataSource,
                    recipesSearchHistoryDataSource = recipesSearchHistoryDataSource,
                    filterRecipesSearchHistoryItems = useCaseModule.filterRecipesSearchHistoryItems
                )
            }
        )

    val addIngredientViewModel: AddIngredientViewModel
        @Composable
        get() = getViewModel(
            key = "add-ingredient-screen",
            factory = viewModelFactory {
                AddIngredientViewModel(
                    validateProduct = useCaseModule.validateProduct
                )
            }
        )

    val addStepViewModel: AddStepViewModel
        @Composable
        get() = getViewModel(
            key = "add-step-screen",
            factory = viewModelFactory {
                AddStepViewModel(
                    validateStep = useCaseModule.validateStep
                )
            }
        )

}
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
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

class ViewModelModule(
    private val productDataSource: ProductDataSource,
    private val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource,
    private val recipeDataSource: RecipeDataSource
) {
    val productsViewModel: ProductsViewModel
        @Composable
        get() = getViewModel(
            key = "products-screen",
            factory = viewModelFactory {
                ProductsViewModel(
                    productDataSource = productDataSource
                )
            }
        )

    val addProductViewModel: AddProductViewModel
        @Composable
        get() = getViewModel(
            key = "add-product-screen",
            factory = viewModelFactory {
                AddProductViewModel(
                    productDataSource = productDataSource
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
                    productsSearchHistoryDataSource = productsSearchHistoryDataSource
                )
            }
        )

    val recipesViewModel: RecipesViewModel
        @Composable
        get() = getViewModel(
            key = "products-search-screen",
            factory = viewModelFactory {
                RecipesViewModel(
                    recipeDataSource = recipeDataSource
                )
            }
        )

    val addRecipeViewModel: AddRecipeViewModel
        @Composable
        get() = getViewModel(
            key = "add-recipe-screen",
            factory = viewModelFactory {
                AddRecipeViewModel(
                    recipeDataSource = recipeDataSource
                )
            }
        )

    val addIngredientViewModel: AddIngredientViewModel
        @Composable
        get() = getViewModel(
            key = "add-recipe-screen",
            factory = viewModelFactory {
                AddIngredientViewModel()
            }
        )

    val addStepViewModel: AddStepViewModel
        @Composable
        get() = getViewModel(
            key = "add-recipe-screen",
            factory = viewModelFactory {
                AddStepViewModel()
            }
        )

}
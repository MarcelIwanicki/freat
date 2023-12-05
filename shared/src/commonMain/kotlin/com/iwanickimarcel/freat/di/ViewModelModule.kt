package com.iwanickimarcel.freat.di

import androidx.compose.runtime.Composable
import com.iwanickimarcel.add_ingredient.AddIngredientViewModel
import com.iwanickimarcel.add_product.AddProductViewModel
import com.iwanickimarcel.add_recipe.AddRecipeViewModel
import com.iwanickimarcel.add_step.AddStepViewModel
import com.iwanickimarcel.freat.feature.home.presentation.HomeViewModel
import com.iwanickimarcel.freat.feature.products_search.presentation.ProductsSearchViewModel
import com.iwanickimarcel.freat.feature.recipes_search.presentation.RecipesSearchViewModel
import com.iwanickimarcel.freat.feature.scan_bill.presentation.ScanBillViewModel
import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.products.ProductsViewModel
import com.iwanickimarcel.products_search.ProductsSearchHistoryDataSource
import com.iwanickimarcel.recipes.RecipeDataSource
import com.iwanickimarcel.recipes.RecipesViewModel
import com.iwanickimarcel.recipes_search.RecipesSearchHistoryDataSource
import com.iwanickimarcel.scan_bill.ImageAnalyzer
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.java.KoinJavaComponent.inject

val viewModelModule: ViewModelModule = ViewModelModule()

class ViewModelModule {
    private val productDataSource: ProductDataSource by inject(
        ProductDataSource::class.java
    )
    private val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource by inject(
        ProductsSearchHistoryDataSource::class.java
    )
    private val recipeDataSource: RecipeDataSource by inject(
        RecipeDataSource::class.java
    )
    private val recipesSearchHistoryDataSource: RecipesSearchHistoryDataSource by inject(
        RecipesSearchHistoryDataSource::class.java
    )
    private val imageAnalyzer: ImageAnalyzer by inject(
        ImageAnalyzer::class.java
    )
    private val useCaseModule: UseCaseModule by lazy {
        UseCaseModule()
    }

    val homeViewModel: HomeViewModel
        @Composable
        get() = getViewModel(
            key = "home-screen",
            factory = viewModelFactory {
                HomeViewModel(
                    productDataSource = productDataSource,
                    recipeDataSource = recipeDataSource
                )
            }
        )

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
            key = "recipes-screen",
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
                    validateRecipe = useCaseModule.validateRecipe,
                    deleteStep = useCaseModule.deleteStep,
                    editStep = useCaseModule.editStep
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

    val scanBillViewModel: ScanBillViewModel
        @Composable
        get() = getViewModel(
            key = "scan-bill-screen",
            factory = viewModelFactory {
                ScanBillViewModel(
                    imageAnalyzer = imageAnalyzer,
                    productDataSource = productDataSource
                )
            }
        )

}
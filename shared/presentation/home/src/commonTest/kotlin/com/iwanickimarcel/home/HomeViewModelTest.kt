package com.iwanickimarcel.home

import app.cash.turbine.test
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.products.g
import com.iwanickimarcel.recipes.Recipe
import com.iwanickimarcel.recipes.RecipeDataSource
import com.iwanickimarcel.test.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var productDataSource: ProductDataSource
    private lateinit var recipeDataSource: RecipeDataSource
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        productDataSource = FakeProductDataSource()
        recipeDataSource = FakeRecipeDataSource()
        viewModel = HomeViewModel(
            productDataSource = productDataSource,
            recipeDataSource = recipeDataSource
        )
    }

    @Test
    fun `initially state with default values should be emitted`() = runTest {
        viewModel.state.test {
            val expectedState = HomeState(
                loading = false
            )
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on search bar click called, state with search bar clicked should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(HomeEvent.OnSearchBarClick)

                val expectedState = HomeState(
                    loading = false,
                    isSearchBarClicked = true
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on show all products click called, state with show all products clicked should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(HomeEvent.OnShowAllProductsClick)

                val expectedState = HomeState(
                    loading = false,
                    isShowAllProductsClicked = true
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on product click called, state with product clicked should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(
                HomeEvent.OnProductClick(
                    product = Product(
                        name = "Apple",
                        amount = 50.g,
                        photoBytes = null
                    )
                )
            )

            val expectedState = HomeState(
                loading = false,
                clickedProduct = Product(
                    name = "Apple",
                    amount = 50.g,
                    photoBytes = null
                )
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on show all recipes click called, state with show all recipes clicked should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(HomeEvent.OnShowAllRecipesClick)

                val expectedState = HomeState(
                    loading = false,
                    isShowAllRecipesClicked = true
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on recipe click called, state with recipe clicked should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(
                HomeEvent.OnRecipeClick(
                    recipe = Recipe(
                        id = 123,
                        name = "Apple pie",
                        photoBytes = null,
                        products = emptyList(),
                        tags = emptyList(),
                        steps = emptyList(),
                        ownedProductsPercent = 0,
                        isFavorite = false
                    )
                )
            )

            val expectedState = HomeState(
                loading = false,
                clickedRecipe = Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = emptyList(),
                    tags = emptyList(),
                    steps = emptyList(),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on scan bill click called, state with scan bill clicked should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(HomeEvent.OnScanBillClick)

                val expectedState = HomeState(
                    loading = false,
                    isScanBillClicked = true
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on favorite click called, recipe data source should have isFavorite modified for this recipe`() =
        runTest {
            recipeDataSource.insertRecipe(
                Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = emptyList(),
                    tags = emptyList(),
                    steps = emptyList(),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            viewModel.onEvent(
                HomeEvent.OnFavoriteClick(
                    recipe = Recipe(
                        id = 123,
                        name = "Apple pie",
                        photoBytes = null,
                        products = emptyList(),
                        tags = emptyList(),
                        steps = emptyList(),
                        ownedProductsPercent = 0,
                        isFavorite = false
                    )
                )
            )

            val expectedRecipe = recipeDataSource.getRecipeById(123)
            assertThat(expectedRecipe.isFavorite).isTrue
        }
}
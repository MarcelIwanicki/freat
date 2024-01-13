package com.iwanickimarcel.add_recipe

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.products.g
import com.iwanickimarcel.products.mg
import com.iwanickimarcel.recipes.Recipe
import com.iwanickimarcel.recipes.RecipeDataSource
import com.iwanickimarcel.test.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddRecipeViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var recipeDataSource: RecipeDataSource
    private lateinit var validateRecipe: ValidateRecipe
    private lateinit var deleteStep: DeleteStep
    private lateinit var editStep: EditStep
    private lateinit var viewModel: AddRecipeViewModel

    @Before
    fun setUp() {
        recipeDataSource = FakeRecipeDataSource()
        validateRecipe = ValidateRecipe()
        deleteStep = DeleteStep()
        editStep = EditStep()
        viewModel = AddRecipeViewModel(
            recipeDataSource = recipeDataSource,
            validateRecipe = validateRecipe,
            deleteStep = deleteStep,
            editStep = editStep
        )
    }

    @Test
    fun `initially state with default values should be emitted`() = runTest {
        viewModel.state.test {
            val expectedState = AddRecipeState()
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on edit recipe provided event called, state with recipe found should be emitted`() =
        runTest {
            recipeDataSource.insertRecipe(
                recipe = Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = listOf(
                        Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        ),
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                    ),
                    tags = listOf(
                        Recipe.Tag("Sweet"),
                        Recipe.Tag("Simple"),
                        Recipe.Tag("Apple"),
                        Recipe.Tag("Pie"),
                    ),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Cook it"
                        ),
                        Recipe.Step(
                            step = 3,
                            description = "Eat it"
                        ),
                    ),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            viewModel.state.test {
                viewModel.onEvent(AddRecipeEvent.OnEditRecipeProvided(123))

                val expectedState = AddRecipeState(
                    editId = 123,
                    name = TextFieldValue("Apple pie"),
                    ingredients = listOf(
                        Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        ),
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                    ),
                    tags = listOf(
                        Recipe.Tag("Sweet"),
                        Recipe.Tag("Simple"),
                        Recipe.Tag("Apple"),
                        Recipe.Tag("Pie"),
                    ),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Cook it"
                        ),
                        Recipe.Step(
                            step = 3,
                            description = "Eat it"
                        ),
                    ),
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on name changed event called, state with name should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(AddRecipeEvent.OnNameChanged(TextFieldValue("Apple pie")))

            val expectedState = AddRecipeState(
                name = TextFieldValue("Apple pie"),
                nameError = null
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on photo selected event called, state with photo bytes should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnPhotoSelected(
                        photoBytes = byteArrayOf(1, 2, 3, 4, 5)
                    )
                )

                val expectedState = AddRecipeState(
                    photoBytes = byteArrayOf(1, 2, 3, 4, 5)
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on add ingredient press event called, state with add ingredient open should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(AddRecipeEvent.OnAddIngredientPress)

                val expectedState = AddRecipeState(
                    addIngredientOpen = true,
                    finalErrorMessage = null
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on add ingredient dismiss event called, state with add ingredient closed should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(AddRecipeEvent.OnAddIngredientPress)
                viewModel.onEvent(AddRecipeEvent.OnAddIngredientDismiss)

                val expectedState = AddRecipeState(
                    addIngredientOpen = false,
                    finalErrorMessage = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on edit ingredient press event called, state with edit ingredient should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditIngredientPress(
                        product = Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        )
                    )
                )

                val expectedState = AddRecipeState(
                    editIngredient = Product(
                        name = "Apple",
                        amount = 100.g,
                        photoBytes = null
                    ),
                    finalErrorMessage = null
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on delete ingredient press event called, state without deleted ingredient should be emitted`() =
        runTest {
            recipeDataSource.insertRecipe(
                recipe = Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = listOf(
                        Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        ),
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                    ),
                    tags = emptyList(),
                    steps = emptyList(),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditRecipeProvided(
                        id = 123
                    )
                )
                viewModel.onEvent(
                    AddRecipeEvent.OnDeleteIngredientPress(
                        product = Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        )
                    )
                )

                val expectedState = AddRecipeState(
                    editId = 123,
                    name = TextFieldValue("Apple pie"),
                    ingredients = listOf(
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                    ),
                    finalErrorMessage = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on edit ingredient dismiss called, state without edit ingredient should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditIngredientPress(
                        product = Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        )
                    )
                )
                viewModel.onEvent(AddRecipeEvent.OnEditIngredientDismiss)

                val expectedState = AddRecipeState(
                    editIngredient = null,
                    finalErrorMessage = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on ingredient added called, state with new ingredient should be emitted`() =
        runTest {
            recipeDataSource.insertRecipe(
                recipe = Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = listOf(
                        Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        ),
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                    ),
                    tags = emptyList(),
                    steps = emptyList(),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditRecipeProvided(
                        id = 123
                    )
                )
                viewModel.onEvent(
                    AddRecipeEvent.OnIngredientAdded(
                        ingredient = Product(
                            name = "Banana",
                            amount = 200.g,
                            photoBytes = null
                        )
                    )
                )

                val expectedState = AddRecipeState(
                    editId = 123,
                    name = TextFieldValue("Apple pie"),
                    ingredients = listOf(
                        Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        ),
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                        Product(
                            name = "Banana",
                            amount = 200.g,
                            photoBytes = null
                        )
                    ),
                    finalErrorMessage = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on ingredient edited called, state with modified ingredient should be emitted`() =
        runTest {
            recipeDataSource.insertRecipe(
                recipe = Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = listOf(
                        Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        ),
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                    ),
                    tags = emptyList(),
                    steps = emptyList(),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditRecipeProvided(
                        id = 123
                    )
                )
                viewModel.onEvent(
                    AddRecipeEvent.OnIngredientEdited(
                        ingredient = Product(
                            name = "Apple",
                            amount = 20.g,
                            photoBytes = null
                        )
                    )
                )

                val expectedState = AddRecipeState(
                    editId = 123,
                    name = TextFieldValue("Apple pie"),
                    ingredients = listOf(
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                        Product(
                            name = "Apple",
                            amount = 20.g,
                            photoBytes = null
                        )
                    ),
                    finalErrorMessage = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on add step press called, state with add step open should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(AddRecipeEvent.OnAddStepPress)

            val expectedState = AddRecipeState(
                addStepOpen = true,
                finalErrorMessage = null,
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on edit step press called, state with edit step open should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(
                AddRecipeEvent.OnEditStepPress(
                    step = Recipe.Step(
                        step = 1,
                        description = "Lorem ipsum"
                    )
                )
            )

            val expectedState = AddRecipeState(
                editStep = Recipe.Step(
                    step = 1,
                    description = "Lorem ipsum"
                ),
                finalErrorMessage = null
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on delete step press called, state with deleted and reordered steps should be emitted`() =
        runTest {
            recipeDataSource.insertRecipe(
                recipe = Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = emptyList(),
                    tags = emptyList(),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Cook it"
                        ),
                        Recipe.Step(
                            step = 3,
                            description = "Eat it"
                        ),
                    ),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditRecipeProvided(
                        id = 123
                    )
                )
                viewModel.onEvent(
                    AddRecipeEvent.OnDeleteStepPress(
                        step = Recipe.Step(
                            step = 2,
                            description = "Cook it"
                        )
                    )
                )

                val expectedState = AddRecipeState(
                    editId = 123,
                    name = TextFieldValue("Apple pie"),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Eat it"
                        ),
                    ),
                    finalErrorMessage = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on add step dismiss called, state with add step closed should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(AddRecipeEvent.OnAddStepPress)
                viewModel.onEvent(AddRecipeEvent.OnAddStepDismiss)

                val expectedState = AddRecipeState(
                    addStepOpen = false,
                    finalErrorMessage = null,
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on edit step dismiss called, state with edit step closed should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditStepPress(
                        step = Recipe.Step(
                            step = 1,
                            description = "Lorem ipsum"
                        )
                    )
                )
                viewModel.onEvent(AddRecipeEvent.OnEditStepDismiss)

                val expectedState = AddRecipeState(
                    editStep = null,
                    finalErrorMessage = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on step added called, state with new step should be emitted`() =
        runTest {
            recipeDataSource.insertRecipe(
                recipe = Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = emptyList(),
                    tags = emptyList(),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Cook it"
                        ),
                        Recipe.Step(
                            step = 3,
                            description = "Eat it"
                        ),
                    ),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditRecipeProvided(
                        id = 123
                    )
                )
                viewModel.onEvent(
                    AddRecipeEvent.OnStepAdded(
                        step = Recipe.Step(
                            step = 4,
                            description = "Enjoy"
                        )
                    )
                )

                val expectedState = AddRecipeState(
                    editId = 123,
                    name = TextFieldValue("Apple pie"),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Cook it"
                        ),
                        Recipe.Step(
                            step = 3,
                            description = "Eat it"
                        ),
                        Recipe.Step(
                            step = 4,
                            description = "Enjoy"
                        )
                    ),
                    finalErrorMessage = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on step edited called, state with modified step should be emitted`() =
        runTest {
            recipeDataSource.insertRecipe(
                recipe = Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = emptyList(),
                    tags = emptyList(),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Cook it"
                        ),
                        Recipe.Step(
                            step = 3,
                            description = "Eat it"
                        ),
                    ),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditRecipeProvided(
                        id = 123
                    )
                )
                viewModel.onEvent(
                    AddRecipeEvent.OnStepEdited(
                        step = Recipe.Step(
                            step = 2,
                            description = "Bake it"
                        )
                    )
                )

                val expectedState = AddRecipeState(
                    editId = 123,
                    name = TextFieldValue("Apple pie"),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Bake it"
                        ),
                        Recipe.Step(
                            step = 3,
                            description = "Eat it"
                        )
                    ),
                    finalErrorMessage = null
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on tag added called, state with new tag should be emitted`() = runTest {
        recipeDataSource.insertRecipe(
            recipe = Recipe(
                id = 123,
                name = "Apple pie",
                photoBytes = null,
                products = emptyList(),
                tags = listOf(
                    Recipe.Tag("Sweet"),
                    Recipe.Tag("Simple"),
                    Recipe.Tag("Apple"),
                    Recipe.Tag("Pie"),
                ),
                steps = emptyList(),
                ownedProductsPercent = 0,
                isFavorite = false
            )
        )

        viewModel.state.test {
            viewModel.onEvent(
                AddRecipeEvent.OnEditRecipeProvided(
                    id = 123
                )
            )
            viewModel.onEvent(
                AddRecipeEvent.OnTagAdded(
                    tagName = "Cool"
                )
            )

            val expectedState = AddRecipeState(
                editId = 123,
                name = TextFieldValue("Apple pie"),
                tags = listOf(
                    Recipe.Tag("Sweet"),
                    Recipe.Tag("Simple"),
                    Recipe.Tag("Apple"),
                    Recipe.Tag("Pie"),
                    Recipe.Tag("Cool"),
                ),
                finalErrorMessage = null
            )

            skipItems(2)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on tag removed called, state without tag should be emitted`() = runTest {
        recipeDataSource.insertRecipe(
            recipe = Recipe(
                id = 123,
                name = "Apple pie",
                photoBytes = null,
                products = emptyList(),
                tags = listOf(
                    Recipe.Tag("Sweet"),
                    Recipe.Tag("Simple"),
                    Recipe.Tag("Apple"),
                    Recipe.Tag("Pie"),
                ),
                steps = emptyList(),
                ownedProductsPercent = 0,
                isFavorite = false
            )
        )

        viewModel.state.test {
            viewModel.onEvent(
                AddRecipeEvent.OnEditRecipeProvided(
                    id = 123
                )
            )
            viewModel.onEvent(
                AddRecipeEvent.OnTagRemoved(
                    index = 2
                )
            )

            val expectedState = AddRecipeState(
                editId = 123,
                name = TextFieldValue("Apple pie"),
                tags = listOf(
                    Recipe.Tag("Sweet"),
                    Recipe.Tag("Simple"),
                    Recipe.Tag("Pie"),
                ),
                finalErrorMessage = null
            )

            skipItems(2)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on tag text field value changed called, state with text field should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnTagTextFieldValueChanged(
                        textFieldValue = TextFieldValue("Lorem ipsum")
                    )
                )

                val expectedState = AddRecipeState(
                    tagsTextFieldValue = TextFieldValue("Lorem ipsum"),
                    finalErrorMessage = null,
                )

                skipItems(1)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on add recipe confirm called and all the data is correct, state with success should be emitted`() =
        runTest {
            recipeDataSource.insertRecipe(
                recipe = Recipe(
                    id = 123,
                    name = "Apple pie",
                    photoBytes = null,
                    products = listOf(
                        Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        ),
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                    ),
                    tags = listOf(
                        Recipe.Tag("Sweet"),
                        Recipe.Tag("Simple"),
                        Recipe.Tag("Apple"),
                        Recipe.Tag("Pie"),
                    ),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Cook it"
                        ),
                        Recipe.Step(
                            step = 3,
                            description = "Eat it"
                        ),
                    ),
                    ownedProductsPercent = 0,
                    isFavorite = false
                )
            )

            viewModel.state.test {
                viewModel.onEvent(
                    AddRecipeEvent.OnEditRecipeProvided(
                        id = 123
                    )
                )
                viewModel.onEvent(AddRecipeEvent.OnAddRecipeConfirm)

                val expectedState = AddRecipeState(
                    editId = 123,
                    name = TextFieldValue("Apple pie"),
                    ingredients = listOf(
                        Product(
                            name = "Apple",
                            amount = 100.g,
                            photoBytes = null
                        ),
                        Product(
                            name = "Sugar",
                            amount = 100.mg,
                            photoBytes = null
                        ),
                    ),
                    tags = listOf(
                        Recipe.Tag("Sweet"),
                        Recipe.Tag("Simple"),
                        Recipe.Tag("Apple"),
                        Recipe.Tag("Pie"),
                    ),
                    steps = listOf(
                        Recipe.Step(
                            step = 1,
                            description = "Mix sugar with apple"
                        ),
                        Recipe.Step(
                            step = 2,
                            description = "Cook it"
                        ),
                        Recipe.Step(
                            step = 3,
                            description = "Eat it"
                        ),
                    ),
                    success = true
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }
}
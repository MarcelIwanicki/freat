package com.iwanickimarcel.freat.feature.add_recipe.presentation

import com.iwanickimarcel.freat.feature.add_recipe.domain.DeleteStep
import com.iwanickimarcel.freat.feature.add_recipe.domain.EditStep
import com.iwanickimarcel.freat.feature.add_recipe.domain.ValidateRecipe
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class AddRecipeViewModel(
    private val recipeDataSource: RecipeDataSource,
    private val validateRecipe: ValidateRecipe,
    private val deleteStep: DeleteStep,
    private val editStep: EditStep
) : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5.seconds
    }

    private val _state = MutableStateFlow(AddRecipeState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(STOP_TIMEOUT),
        AddRecipeState()
    )

    fun onEvent(event: AddRecipeEvent) {
        when (event) {
            is AddRecipeEvent.OnEditRecipeProvided -> {
                viewModelScope.launch {
                    val recipe = recipeDataSource.getRecipeById(event.id)
                    _state.value = _state.value.copy(
                        editId = recipe.id,
                        name = recipe.name,
                        photoBytes = recipe.photoBytes,
                        ingredients = recipe.products,
                        steps = recipe.steps,
                        tags = recipe.tags
                    )
                }
            }

            is AddRecipeEvent.OnNameChanged -> {
                _state.value = _state.value.copy(
                    name = event.name,
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnPhotoSelected -> {
                _state.value = _state.value.copy(
                    photoBytes = event.photoBytes,
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnAddIngredientPress -> {
                _state.value = _state.value.copy(
                    addIngredientOpen = true,
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnAddIngredientDismiss -> {
                _state.value = _state.value.copy(
                    addIngredientOpen = false,
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnEditIngredientPress -> {
                _state.value = _state.value.copy(
                    editIngredient = event.product,
                    finalErrorMessage = null
                )
            }

            is AddRecipeEvent.OnDeleteIngredientPress -> {
                _state.value = _state.value.copy(
                    ingredients = _state.value.ingredients.toMutableList().apply {
                        remove(event.product)
                    },
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnEditIngredientDismiss -> {
                _state.value = _state.value.copy(
                    editIngredient = null,
                    finalErrorMessage = null
                )
            }

            is AddRecipeEvent.OnIngredientAdded -> {
                _state.value = _state.value.copy(
                    ingredients = _state.value.ingredients.toMutableList().apply {
                        add(event.ingredient)
                    },
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnIngredientEdited -> {
                _state.value = _state.value.copy(
                    ingredients = _state.value.ingredients.toMutableList().apply {
                        remove(_state.value.ingredients.find { it.name == event.ingredient.name })
                        add(event.ingredient)
                    },
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnAddStepPress -> {
                _state.value = _state.value.copy(
                    addStepOpen = true,
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnEditStepPress -> {
                _state.value = _state.value.copy(
                    editStep = event.step,
                    finalErrorMessage = null
                )
            }

            is AddRecipeEvent.OnDeleteStepPress -> {
                _state.value = _state.value.copy(
                    steps = deleteStep(
                        steps = _state.value.steps,
                        stepToDelete = event.step
                    ),
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnAddStepDismiss -> {
                _state.value = _state.value.copy(
                    addStepOpen = false,
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnEditStepDismiss -> {
                _state.value = _state.value.copy(
                    editStep = null,
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnStepAdded -> {
                _state.value = _state.value.copy(
                    steps = _state.value.steps.toMutableList().apply {
                        add(event.step)
                    },
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnStepEdited -> {
                _state.value = _state.value.copy(
                    steps = editStep(
                        steps = _state.value.steps,
                        stepToEdit = event.step
                    )
                )
            }

            is AddRecipeEvent.OnTagAdded -> {
                _state.value = _state.value.copy(
                    tags = _state.value.tags.toMutableList().apply {
                        add(Recipe.Tag(event.tagName))
                    },
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnTagRemoved -> {
                _state.value = _state.value.copy(
                    tags = _state.value.tags.toMutableList().apply {
                        removeAt(event.index)
                    },
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnTagTextFieldValueChanged -> {
                _state.value = _state.value.copy(
                    tagsTextFieldValue = event.textFieldValue,
                    finalErrorMessage = null,
                )
            }

            is AddRecipeEvent.OnAddRecipeConfirm -> with(_state.value) {
                viewModelScope.launch {
                    validateRecipe(
                        editId = editId,
                        name = name,
                        ingredients = ingredients,
                        steps = steps,
                        tags = tags,
                        photoBytes = photoBytes,
                        onDeleteRecipe = recipeDataSource::deleteRecipe,
                        onInsertRecipe = recipeDataSource::insertRecipe,
                        onSuccess = {
                            _state.value = _state.value.copy(
                                success = true
                            )
                        },
                        onError = {
                            _state.value = _state.value.copy(
                                finalErrorMessage = it
                            )
                        }
                    )
                }
            }
        }
    }
}
package com.iwanickimarcel.freat.feature.add_recipe.presentation

import com.iwanickimarcel.freat.core.extensions.generateUniqueId
import com.iwanickimarcel.freat.feature.add_recipe.domain.RecipeValidator
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddRecipeViewModel(
    private val recipeDataSource: RecipeDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(AddRecipeState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
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

            is AddRecipeEvent.OnIngredientAdded -> {
                _state.value = _state.value.copy(
                    ingredients = _state.value.ingredients.toMutableList().apply {
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

            is AddRecipeEvent.OnAddStepDismiss -> {
                _state.value = _state.value.copy(
                    addStepOpen = false,
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
                val validations = listOf(
                    RecipeValidator.validateName(name),
                    RecipeValidator.validateIngredients(ingredients),
                    RecipeValidator.validateSteps(steps),
                    RecipeValidator.validateTags(tags)
                )

                if (validations.all { it == null }) {
                    viewModelScope.launch {
                        val recipe = Recipe(
                            id = editId ?: generateUniqueId(),
                            name = name ?: return@launch,
                            photoBytes = photoBytes,
                            products = ingredients,
                            tags = tags,
                            steps = steps
                        )

                        if (editId != null) {
                            recipeDataSource.deleteRecipe(editId)
                        }
                        recipeDataSource.insertRecipe(recipe)

                        _state.value = _state.value.copy(
                            success = true
                        )
                    }

                    return@with
                }

                validations.find { it != null }?.let {
                    _state.value = _state.value.copy(
                        finalErrorMessage = it
                    )
                }
            }
        }
    }
}
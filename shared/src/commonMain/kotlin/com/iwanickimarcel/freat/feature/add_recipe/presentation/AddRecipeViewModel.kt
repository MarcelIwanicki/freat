package com.iwanickimarcel.freat.feature.add_recipe.presentation

import com.iwanickimarcel.freat.core.extensions.generateUniqueId
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
            is AddRecipeEvent.OnNameChanged -> {
                _state.value = _state.value.copy(
                    name = event.name,
                    nameError = null
                )
            }

            is AddRecipeEvent.OnPhotoSelected -> {
                _state.value = _state.value.copy(
                    photoBytes = event.photoBytes
                )
            }

            is AddRecipeEvent.OnAddIngredientPress -> {
                _state.value = _state.value.copy(
                    addIngredientOpen = true
                )
            }

            is AddRecipeEvent.OnAddIngredientDismiss -> {
                _state.value = _state.value.copy(
                    addIngredientOpen = false
                )
            }

            is AddRecipeEvent.OnIngredientAdded -> {
                _state.value = _state.value.copy(
                    ingredients = _state.value.ingredients.toMutableList().apply {
                        add(event.ingredient)
                    }
                )
            }

            is AddRecipeEvent.OnAddStepPress -> {
                _state.value = _state.value.copy(
                    addStepOpen = true
                )
            }

            is AddRecipeEvent.OnAddStepDismiss -> {
                _state.value = _state.value.copy(
                    addStepOpen = false
                )
            }

            is AddRecipeEvent.OnStepAdded -> {
                _state.value = _state.value.copy(
                    steps = _state.value.steps.toMutableList().apply {
                        add(event.step)
                    }
                )
            }

            is AddRecipeEvent.OnTagAdded -> {
                _state.value = _state.value.copy(
                    tags = _state.value.tags.toMutableList().apply {
                        add(Recipe.Tag(event.tagName))
                    }
                )
            }

            is AddRecipeEvent.OnTagRemoved -> {
                _state.value = _state.value.copy(
                    tags = _state.value.tags.toMutableList().apply {
                        removeAt(event.index)
                    }
                )
            }

            is AddRecipeEvent.OnTagTextFieldValueChanged -> {
                _state.value = _state.value.copy(
                    tagsTextFieldValue = event.textFieldValue
                )
            }

            is AddRecipeEvent.OnAddRecipeConfirm -> {
                viewModelScope.launch {
                    val recipe = Recipe(
                        id = generateUniqueId(),
                        name = _state.value.name ?: return@launch,
                        photoBytes = _state.value.photoBytes ?: return@launch,
                        products = _state.value.ingredients,
                        tags = _state.value.tags,
                        steps = _state.value.steps
                    )

                    recipeDataSource.insertRecipe(recipe)

                    _state.value = _state.value.copy(
                        success = true
                    )
                }
            }
        }
    }
}
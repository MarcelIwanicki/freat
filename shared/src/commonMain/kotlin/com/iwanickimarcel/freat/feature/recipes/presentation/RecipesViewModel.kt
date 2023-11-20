package com.iwanickimarcel.freat.feature.recipes.presentation

import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val recipeDataSource: RecipeDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(RecipesState())
    val state = combine(
        _state,
        recipeDataSource.getRecipes()
    ) { state, recipes ->
        state.copy(
            recipes = recipes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipesState())

    fun onEvent(event: RecipesEvent) {
        when (event) {
            is RecipesEvent.OnRecipeLongPress -> {
                _state.value = _state.value.copy(
                    longPressedRecipe = event.recipe
                )
            }

            is RecipesEvent.OnRecipeLongPressDismiss -> {
                _state.value = _state.value.copy(
                    longPressedRecipe = null
                )
            }

            is RecipesEvent.OnDeleteRecipePress -> {
                _state.value = _state.value.copy(
                    recipeToDelete = event.recipe,
                    longPressedRecipe = null
                )
            }

            is RecipesEvent.OnDeleteRecipeConfirm -> {
                val recipe = _state.value.recipeToDelete ?: return

                viewModelScope.launch {
                    recipeDataSource.deleteRecipe(recipe.id)
                }

                _state.value = _state.value.copy(
                    recipeToDelete = null
                )
            }

            is RecipesEvent.OnDeleteRecipeMenuDismiss -> {
                _state.value = _state.value.copy(
                    recipeToDelete = null
                )
            }

            is RecipesEvent.OnEditRecipePress -> {
                _state.value = _state.value.copy(
                    recipeToEdit = event.recipe,
                    longPressedRecipe = null
                )
            }

            else -> {}
        }
    }
}
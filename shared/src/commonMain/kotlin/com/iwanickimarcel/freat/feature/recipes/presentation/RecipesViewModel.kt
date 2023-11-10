package com.iwanickimarcel.freat.feature.recipes.presentation

import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

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

}
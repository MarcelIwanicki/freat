package com.iwanickimarcel.freat.feature.add_step.presentation

import com.iwanickimarcel.freat.feature.add_ingredient.domain.StepValidator
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddStepViewModel : ViewModel() {

    private val _state = MutableStateFlow(AddStepState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        AddStepState()
    )

    fun onEvent(event: AddStepEvent) {
        when (event) {
            is AddStepEvent.OnStepChanged -> {
                _state.value = _state.value.copy(
                    step = event.step,
                    stepError = null
                )
            }

            is AddStepEvent.OnAddStepClick -> {
                with(_state.value) {
                    val stepValidation = StepValidator.validateStep(step)

                    if (stepValidation == null) {
                        viewModelScope.launch {
                            event.onStepAdded(
                                Recipe.Step(
                                    step = event.stepsCount + 1,
                                    description = step ?: return@launch
                                )
                            )
                            _state.value = _state.value.copy(
                                success = true
                            )
                        }

                        return@with
                    }

                    _state.value = _state.value.copy(
                        stepError = stepValidation
                    )
                }
            }
        }
    }
}
package com.iwanickimarcel.freat.feature.add_step.presentation

import com.iwanickimarcel.add_step.ValidateStep
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class AddStepViewModel(
    private val validateStep: ValidateStep
) : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5.seconds
    }

    private val _state = MutableStateFlow(AddStepState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(STOP_TIMEOUT),
        AddStepState()
    )

    fun onEvent(event: AddStepEvent) {
        when (event) {
            is AddStepEvent.OnEditStepProvided -> {
                _state.value = _state.value.copy(
                    step = event.step.description,
                )
            }

            is AddStepEvent.OnStepChanged -> {
                _state.value = _state.value.copy(
                    step = event.step,
                    stepError = null
                )
            }

            is AddStepEvent.OnAddStepClick -> with(_state.value) {
                viewModelScope.launch {
                    validateStep(
                        step = step,
                        stepsCount = event.stepsCount,
                        onStepAdded = event.onStepAdded,
                        onSuccess = {
                            _state.value = _state.value.copy(
                                success = true
                            )
                        },
                        onError = {
                            _state.value = _state.value.copy(
                                stepError = it
                            )
                        }
                    )
                }
            }
        }
    }
}
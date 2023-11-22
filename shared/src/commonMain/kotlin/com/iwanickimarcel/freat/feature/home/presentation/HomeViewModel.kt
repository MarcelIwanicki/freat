package com.iwanickimarcel.freat.feature.home.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

class HomeViewModel : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5.seconds
    }

    private val _state = MutableStateFlow(HomeState())
    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT), HomeState()
    )

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSearchBarClick -> {
                _state.value = _state.value.copy(
                    isSearchBarClicked = true
                )
            }
        }
    }
}
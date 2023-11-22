package com.iwanickimarcel.freat.feature.home.presentation

sealed interface HomeEvent {
    object OnSearchBarClick : HomeEvent
}
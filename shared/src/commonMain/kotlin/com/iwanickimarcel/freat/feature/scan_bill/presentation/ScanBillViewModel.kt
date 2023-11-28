package com.iwanickimarcel.freat.feature.scan_bill.presentation

import com.iwanickimarcel.freat.core.domain.ImageAnalyzer
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ScanBillViewModel(
    val imageAnalyzer: ImageAnalyzer
) : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5.seconds
    }

    private val _state = MutableStateFlow(ScanBillState())
    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT), ScanBillState()
    )

    fun onEvent(event: ScanBillEvent) {
        when (event) {
            is ScanBillEvent.OnImageAnalysisRequested -> {
                viewModelScope.launch {
                    imageAnalyzer.getProductsFromImage(event.imageBitmap).collect {
                        _state.value = _state.value.copy(
                            products = it
                        )
                    }
                }
            }

            is ScanBillEvent.OnPhotoSelected -> {
                _state.value = _state.value.copy(
                    photoBytes = event.photoBytes
                )
            }

            is ScanBillEvent.OnAddProductPress -> {

            }

            is ScanBillEvent.OnDeleteProductPress -> {

            }

            is ScanBillEvent.OnEditProductPress -> {

            }
        }
    }

}
package com.iwanickimarcel.freat.feature.scan_bill.presentation

import androidx.compose.ui.graphics.ImageBitmap
import com.iwanickimarcel.freat.feature.products.domain.Product

sealed interface ScanBillEvent {
    data class OnImageAnalysisRequested(val imageBitmap: ImageBitmap): ScanBillEvent
    data class OnPhotoSelected(val photoBytes: ByteArray) : ScanBillEvent
    data class OnEditProductPress(val product: Product) : ScanBillEvent
    object OnAddProductPress : ScanBillEvent
    data class OnDeleteProductPress(val product: Product) : ScanBillEvent
}
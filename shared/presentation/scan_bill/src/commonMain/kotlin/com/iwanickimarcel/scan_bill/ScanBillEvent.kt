package com.iwanickimarcel.scan_bill

import androidx.compose.ui.graphics.ImageBitmap
import com.iwanickimarcel.products.Product

sealed interface ScanBillEvent {
    data class OnImageAnalysisRequested(val imageBitmap: ImageBitmap) : ScanBillEvent
    data class OnPhotoSelected(val photoBytes: ByteArray) : ScanBillEvent
    data class OnEditProductPress(val product: Product) : ScanBillEvent
    data class OnProductEdited(val product: Product) : ScanBillEvent
    object OnEditProductDismiss : ScanBillEvent
    object OnAddProductPress : ScanBillEvent
    object OnAddProductDismiss : ScanBillEvent
    data class OnProductAdded(val product: Product) : ScanBillEvent
    data class OnDeleteProductPress(val product: Product) : ScanBillEvent
    object OnAddProductsConfirm : ScanBillEvent
}
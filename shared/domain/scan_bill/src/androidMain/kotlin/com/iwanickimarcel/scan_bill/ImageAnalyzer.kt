package com.iwanickimarcel.scan_bill

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.products.g
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

data class Rectangle<T : Number>(
    val startX: T,
    val endX: T,
    val topY: T,
    val bottomY: T
)

data class BillItem(
    val text: String,
    val rectangle: Rectangle<Int>
)

private fun String.isPrice() = matches(Regex("(\\d+)[.,]?(\\d+)?"))

private fun List<BillItem>.findAtSameHeightAs(item: BillItem) = find {
    if (it == item) {
        return@find false
    }

    ((item.rectangle.topY..item.rectangle.bottomY).contains(it.rectangle.topY))
            || ((it.rectangle.topY..it.rectangle.bottomY).contains(item.rectangle.bottomY))
            || ((item.rectangle.topY..item.rectangle.bottomY).contains(it.rectangle.bottomY))
            || ((it.rectangle.topY..it.rectangle.bottomY).contains(item.rectangle.topY))
}

actual class ImageAnalyzer {

    private val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    actual fun getProductsFromImage(imageBitmap: ImageBitmap): Flow<List<Product>> = callbackFlow {
        textRecognizer.process(
            getInputImage(imageBitmap.asAndroidBitmap())
        ).addOnSuccessListener { visionText ->
            val items = mutableListOf<BillItem>()
            val pricesItems = mutableListOf<BillItem>()

            visionText.textBlocks.forEach { block ->
                block.lines.forEach { line ->
                    items.add(
                        BillItem(
                            text = line.text,
                            rectangle = line.boundingBox?.run {
                                Rectangle(
                                    startX = left,
                                    endX = right,
                                    topY = top,
                                    bottomY = bottom
                                )
                            } ?: Rectangle(0, 0, 0, 0)
                        )
                    )
                }
            }

            items.forEach { item ->
                if (item.text.isPrice()) {
                    items.findAtSameHeightAs(item)?.let {
                        pricesItems.add(it)
                    }
                }
            }

            val result = channel.trySend(
                pricesItems.map {
                    Product(
                        name = it.text,
                        amount = 0.g,
                        photoBytes = null
                    )
                }
            )
            result.exceptionOrNull()?.let { throw it }
        }.addOnFailureListener { _ ->

        }

        awaitClose()
    }

    private fun getInputImage(
        bitmap: Bitmap
    ) = InputImage.fromBitmap(
        bitmap, 0
    )
}
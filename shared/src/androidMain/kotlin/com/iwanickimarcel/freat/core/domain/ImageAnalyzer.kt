package com.iwanickimarcel.freat.core.domain

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.iwanickimarcel.freat.feature.products.domain.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class ImageAnalyzer {

    private val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    actual fun getProductsFromImage(imageBitmap: ImageBitmap): Flow<List<Product>> = flow {
        textRecognizer.process(
            getInputImage(imageBitmap.asAndroidBitmap())
        ).addOnSuccessListener { visionText ->
            val resultText = visionText.text
            for (block in visionText.textBlocks) {
                val blockText = block.text
                val blockCornerPoints = block.cornerPoints
                val blockFrame = block.boundingBox
                for (line in block.lines) {
                    val lineText = line.text
                    val lineCornerPoints = line.cornerPoints
                    val lineFrame = line.boundingBox
                    for (element in line.elements) {
                        val elementText = element.text
                        val elementCornerPoints = element.cornerPoints
                        val elementFrame = element.boundingBox
                    }
                }
            }
        }.addOnFailureListener { _ ->

        }
    }

    private fun getInputImage(
        bitmap: Bitmap
    ) = InputImage.fromBitmap(
        bitmap, 0
    )
}
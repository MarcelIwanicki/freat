package com.iwanickimarcel.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.iwanickimarcel.core.rememberBitmapFromBytes

@Composable
fun RecipePhoto(
    recipe: Recipe?,
    modifier: Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp)
) {
    val bitmap = rememberBitmapFromBytes(recipe?.photoBytes)
    val photoModifier = modifier.clip(shape)
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
        startY = sizeImage.height.toFloat() / 3,
        endY = sizeImage.height.toFloat()
    )

    bitmap?.let { imageBitmap ->
        Image(
            bitmap = imageBitmap,
            contentDescription = recipe?.name,
            modifier = photoModifier.onGloballyPositioned {
                sizeImage = it.size
            },
            contentScale = ContentScale.Crop
        )
        Box(modifier = photoModifier.background(gradient))
    } ?: run {
        Box(
            modifier = photoModifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}
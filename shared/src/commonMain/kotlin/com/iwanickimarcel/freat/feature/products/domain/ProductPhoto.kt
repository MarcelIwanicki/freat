package com.iwanickimarcel.freat.feature.products.domain

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.iwanickimarcel.freat.core.presentation.rememberBitmapFromBytes

@Composable
fun ProductPhoto(
    product: Product?,
    modifier: Modifier = Modifier,
    iconSize: Dp = 25.dp
) {
    val bitmap = rememberBitmapFromBytes(product?.photoBytes)
    val photoModifier = modifier.clip(RoundedCornerShape(percent = 35))

    bitmap?.let {
        Image(
            bitmap = it,
            contentDescription = product?.name,
            modifier = photoModifier,
            contentScale = ContentScale.Crop
        )
    } ?: run {
        Box(
            modifier = photoModifier
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Fastfood,
                contentDescription = product?.name,
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
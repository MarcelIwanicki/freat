package com.iwanickimarcel.freat.core.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp

@Composable
fun TriangleShape(
    width: Dp,
    height: Dp,
    color: Color
) {
    Canvas(
        modifier = Modifier
            .width(width)
            .height(height),
        onDraw = {
            val trianglePath = Path().apply {
                moveTo(width.toPx() / 2f, 0f)
                lineTo(width.toPx(), height.toPx())
                lineTo(0f, height.toPx())
            }
            drawPath(
                color = color,
                path = trianglePath
            )
        }
    )
}
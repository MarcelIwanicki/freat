package com.iwanickimarcel.core

import androidx.compose.ui.graphics.Color

fun Color.brighten(percentage: Float): Color {
    val factor = 1.0f + percentage / 100.0f
    val red = (this.red * factor).coerceIn(0.0f, 1.0f)
    val green = (this.green * factor).coerceIn(0.0f, 1.0f)
    val blue = (this.blue * factor).coerceIn(0.0f, 1.0f)
    return Color(red, green, blue, alpha)
}

fun Color.darken(percentage: Float): Color {
    val factor = 1.0f - percentage / 100.0f
    val red = (this.red * factor).coerceIn(0.0f, 1.0f)
    val green = (this.green * factor).coerceIn(0.0f, 1.0f)
    val blue = (this.blue * factor).coerceIn(0.0f, 1.0f)
    return Color(red, green, blue, alpha)
}
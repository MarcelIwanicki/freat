package com.iwanickimarcel.core

fun Long.toBoolean() = this > 0

fun Boolean.toLong() = if (this) 1L else 0L
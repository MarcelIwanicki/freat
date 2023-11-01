package com.iwanickimarcel.freat.feature.products.domain

data class Amount(
    val amount: Double,
    val unit: AmountUnit
)

enum class AmountUnit(val abbreviation: String) {
    MilliGram("mg"),
    DecaGram("dg"),
    Gram("g"),
    KiloGram("kg"),
    MilliLiter("ml"),
    Liter("l"),
}

val Double.mg: Amount
    get() = Amount(
        amount = this,
        unit = AmountUnit.MilliGram
    )

val Double.dg: Amount
    get() = Amount(
        amount = this,
        unit = AmountUnit.DecaGram
    )

val Double.g: Amount
    get() = Amount(
        amount = this,
        unit = AmountUnit.Gram
    )

val Double.kg: Amount
    get() = Amount(
        amount = this,
        unit = AmountUnit.KiloGram
    )

val Double.ml: Amount
    get() = Amount(
        amount = this,
        unit = AmountUnit.MilliLiter
    )

val Double.liter: Amount
    get() = Amount(
        amount = this,
        unit = AmountUnit.Liter
    )
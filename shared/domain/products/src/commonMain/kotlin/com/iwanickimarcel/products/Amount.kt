package com.iwanickimarcel.products

data class Amount(
    val amount: Double,
    val unit: AmountUnit
) {
    fun toGrams() = amount * unit.toGrams
}

enum class AmountUnit(
    val abbreviation: String,
    val toGrams: Double
) {
    MilliGram("mg", 0.001),
    DecaGram("dg", 10.0),
    Gram("g", 1.0),
    KiloGram("kg", 1000.0),
    MilliLiter("ml", 1.0),
    Liter("l", 1000.0),
}

val <T : Number> T.mg: Amount
    get() = Amount(
        amount = toDouble(),
        unit = AmountUnit.MilliGram
    )

val <T : Number> T.dg: Amount
    get() = Amount(
        amount = toDouble(),
        unit = AmountUnit.DecaGram
    )

val <T : Number> T.g: Amount
    get() = Amount(
        amount = toDouble(),
        unit = AmountUnit.Gram
    )

val <T : Number> T.kg: Amount
    get() = Amount(
        amount = toDouble(),
        unit = AmountUnit.KiloGram
    )

val <T : Number> T.ml: Amount
    get() = Amount(
        amount = toDouble(),
        unit = AmountUnit.MilliLiter
    )

val <T : Number> T.l: Amount
    get() = Amount(
        amount = toDouble(),
        unit = AmountUnit.Liter
    )
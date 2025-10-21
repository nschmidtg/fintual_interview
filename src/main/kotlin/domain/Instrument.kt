package com.nschmidtg.domain

abstract class Instrument(
    val currentPrice: Double,
    val name: String,
    val symbol: String,
    private val quantityStrategy: QuantityStrategy,
) {

    fun calculateTargetQuantity(targetValue: Double): Result<Double> {
        return quantityStrategy.calculateTargetQuantity(targetValue, currentPrice)
    }
}

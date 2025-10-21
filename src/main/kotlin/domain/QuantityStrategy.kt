package com.nschmidtg.domain

interface QuantityStrategy {
    fun calculateTargetQuantity(targetValue: Double, currentPrice: Double): Result<Double>
}

class WholeUnitsStrategy : QuantityStrategy {
    override fun calculateTargetQuantity(targetValue: Double, currentPrice: Double): Result<Double> = runCatching {
        currentPrice.takeIf { it > 0 }?.let { kotlin.math.floor(targetValue / currentPrice) }
            ?: throw RuntimeException("currentPrice = 0. Cannot compute")
    }
}

class FractionalUnitsStrategy : QuantityStrategy {
    override fun calculateTargetQuantity(targetValue: Double, currentPrice: Double): Result<Double> = runCatching {
        currentPrice.takeIf { it > 0 }?.let { targetValue / currentPrice }
            ?: throw RuntimeException("currentPrice = 0. Cannot compute")
    }
}

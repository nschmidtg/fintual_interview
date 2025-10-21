package com.nschmidtg.domain

data class TargetPortfolio(
    val targetAllocations: Map<Instrument, Double> = hashMapOf()
)

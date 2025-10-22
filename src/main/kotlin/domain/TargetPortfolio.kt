package com.nschmidtg.domain

data class TargetPortfolio(val targetAllocations: Map<Instrument, Double>) {
    init {
        val totalPercentage =
            targetAllocations.entries.sumOf { (_, percentage) ->
                if (percentage < 0.0 || percentage > 1.0) {
                    throw IllegalArgumentException(
                        "Individual target allocation percentages must be between 0.0 and 1.0, but was $percentage"
                    )
                }
                percentage
            }
        if (totalPercentage != 1.0) {
            throw IllegalArgumentException(
                "Total target allocation percentages must sum to 1.0, but was $totalPercentage"
            )
        }
    }
}

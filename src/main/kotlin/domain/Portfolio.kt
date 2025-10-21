package com.nschmidtg.domain

data class Portfolio(var holdings: HashMap<Instrument, Double>) {

    fun calculateTotalValue(): Double =
        holdings.entries.sumOf { (instrument, quantity) ->
            instrument.currentPrice * quantity
        }
}

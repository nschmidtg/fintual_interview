package com.nschmidtg.domain

class Stock(currentPrice: Double, name: String, symbol: String, wholeUnitsStrategy: QuantityStrategy) :
    Instrument(currentPrice, name, symbol, wholeUnitsStrategy) {
    override fun toString(): String {
        return "Stock($symbol)"
    }
}

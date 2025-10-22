package com.nschmidtg.domain

class MutualFund(currentPrice: Double, name: String, symbol: String, fractionalUnitsStrategy: FractionalUnitsStrategy) :
    Instrument(currentPrice, name, symbol, fractionalUnitsStrategy) {

    override fun toString(): String {
        return "MutualFund($symbol)"
    }
}

package com.nschmidtg.domain

class Stock(
    currentPrice: Double,
    name: String,
    symbol: String,
    wholeUnitsStrategy: WholeUnitsStrategy
) : Instrument(currentPrice, name, symbol, wholeUnitsStrategy) {}

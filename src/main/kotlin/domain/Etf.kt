package com.nschmidtg.domain

class Etf(
    currentPrice: Double,
    name: String,
    symbol: String,
    wholeUnitsStrategy: WholeUnitsStrategy
) : Instrument(currentPrice, name, symbol, wholeUnitsStrategy) {}

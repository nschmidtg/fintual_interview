package com.nschmidtg.domain

class Crypto(
    currentPrice: Double,
    name: String,
    symbol: String,
    fractionalUnitsStrategy: FractionalUnitsStrategy
) : Instrument(currentPrice, name, symbol, fractionalUnitsStrategy) {}

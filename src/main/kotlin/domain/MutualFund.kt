package com.nschmidtg.domain

class MutualFund(currentPrice: Double, name: String, symbol: String, fractionalUnitsStrategy: FractionalUnitsStrategy) :
    Instrument(currentPrice, name, symbol, fractionalUnitsStrategy) {}

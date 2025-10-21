package com.nschmidtg.application.dtos

import com.nschmidtg.domain.Instrument

data class TradeOrderDto(
    val instrument: Instrument,
    val quantity: Double,
)

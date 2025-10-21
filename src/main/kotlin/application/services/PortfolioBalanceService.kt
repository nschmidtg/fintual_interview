package com.nschmidtg.application.services

import com.nschmidtg.application.dtos.TradeOrderDto
import com.nschmidtg.domain.Instrument
import com.nschmidtg.domain.Portfolio
import com.nschmidtg.domain.TargetPortfolio

class PortfolioBalanceService {

    fun balance(
        portfolio: Portfolio,
        target: TargetPortfolio
    ): List<TradeOrderDto> {
        val totalValue: Double = portfolio.calculateTotalValue()
        return target.targetAllocations.map {
            (targetInstrument: Instrument, desiredPercentage: Double) ->
            val desiredValue = totalValue * desiredPercentage
            val targetQuantity =
                targetInstrument
                    .calculateTargetQuantity(desiredValue)
                    .getOrThrow()
            val currentQuantity =
                portfolio.holdings.getOrDefault(targetInstrument, 0.0)
            val amountToTrade = targetQuantity - currentQuantity
            TradeOrderDto(targetInstrument, amountToTrade)
        }
    }
}

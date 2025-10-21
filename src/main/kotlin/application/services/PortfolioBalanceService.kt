package com.nschmidtg.application.services

import com.nschmidtg.application.dtos.TradeOrderDto
import com.nschmidtg.domain.Instrument
import com.nschmidtg.domain.Portfolio
import com.nschmidtg.domain.TargetPortfolio

class PortfolioBalanceService {

    fun balance(portfolio: Portfolio, target: TargetPortfolio): Set<TradeOrderDto> {
        if (target.targetAllocations.isEmpty()) throw RuntimeException("TargetPortfolio cannot be empty")
        val totalPortfolioValue: Double = portfolio.calculateTotalValue()
        if (totalPortfolioValue == 0.0) throw RuntimeException("Cannot balance if portfolio value is zero")
        return target.targetAllocations
            .map { (targetInstrument: Instrument, targetInstrumentPercentage: Double) ->
                val desiredValueForCurrentInstrument = totalPortfolioValue * targetInstrumentPercentage
                val targetQuantity =
                    targetInstrument.calculateTargetQuantity(desiredValueForCurrentInstrument).getOrThrow()
                val currentQuantity = portfolio.getHoldingQuantityByInstrument(targetInstrument)
                val amountToTrade = targetQuantity - currentQuantity // negative -> sell, positive -> buy

                TradeOrderDto(targetInstrument, amountToTrade)
            }
            .toSet()
    }
}

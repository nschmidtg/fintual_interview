package com.nschmidtg.application.services

import com.nschmidtg.application.dtos.TradeOrderDto
import com.nschmidtg.domain.Portfolio

class TradingService {

    fun processTradeOperations(tradeOrders: Set<TradeOrderDto>, currentPortfolio: Portfolio): Pair<Portfolio, Double> {
        val newPortfolio = currentPortfolio.copy()
        var freeCash = 0.0
        tradeOrders.forEach { tradeOrder ->
            currentPortfolio.holdings[tradeOrder.instrument]?.let { oldQuantity ->
                val action = if (tradeOrder.quantity < 0) "Selling" else "Buying"
                val newQuantity = oldQuantity + tradeOrder.quantity
                val valueToTrade = "%.2f".format(tradeOrder.quantity * tradeOrder.instrument.currentPrice)
                println(
                    "Instrument: ${tradeOrder.instrument.symbol}, Current Quantity: $oldQuantity, $action Quantity: ${tradeOrder.quantity}. Value to trade $$valueToTrade. New Quantity: $newQuantity. New Instrument Value: $${newQuantity * tradeOrder.instrument.currentPrice}"
                )
                newPortfolio.holdings[tradeOrder.instrument] = oldQuantity + tradeOrder.quantity
                freeCash += tradeOrder.quantity * tradeOrder.instrument.currentPrice
            }
        }
        println()
        freeCash = kotlin.math.abs(-freeCash)

        return Pair(newPortfolio, freeCash)
    }
}

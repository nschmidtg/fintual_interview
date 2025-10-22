package com.nschmidtg

import com.nschmidtg.application.services.PortfolioBalanceService
import com.nschmidtg.application.services.TradingService
import com.nschmidtg.domain.FractionalUnitsStrategy
import com.nschmidtg.domain.MutualFund
import com.nschmidtg.domain.Portfolio
import com.nschmidtg.domain.Stock
import com.nschmidtg.domain.TargetPortfolio
import com.nschmidtg.domain.WholeUnitsStrategy

fun main() {
    val balanceService = PortfolioBalanceService()
    val tradingService = TradingService()

    val instrument1 = Stock(100.0, "Company A", "CMPA", WholeUnitsStrategy())
    val instrument2 = Stock(50.0, "Company B", "CMPB", WholeUnitsStrategy())
    val instrument3 = MutualFund(25.0, "Mutual Fund X", "MFX", FractionalUnitsStrategy())

    val currentPortfolio =
        Portfolio(
            hashMapOf(instrument1 to 10.0, instrument2 to 20.0, instrument3 to 40.0)
        ) // totalValue = 1000 + 1000 + 1000 = 3000

    val targetPortfolio =
        TargetPortfolio(
            mapOf(
                instrument1 to 0.4, // 40%
                instrument2 to 0.34, // 40%
                instrument3 to 0.26 // 20%
            )
        )

    printInitialPortfolio(currentPortfolio)
    printTargetPortfolio(targetPortfolio)

    val tradeOrders = balanceService.balance(currentPortfolio, targetPortfolio)

    val balancedPortfolioToFreeCash: Pair<Portfolio, Double> =
        tradingService.processTradeOperations(tradeOrders, currentPortfolio)

    val finalTotalValue = balancedPortfolioToFreeCash.first.calculateTotalValue()
    printTotalValueAndFreeCash(finalTotalValue, balancedPortfolioToFreeCash)
}

private fun printTotalValueAndFreeCash(finalTotalValue: Double, balancedPortfolioToFreeCash: Pair<Portfolio, Double>) {
    println(
        "Final Portfolio Total Value: $finalTotalValue with Free Cash: $${"%.2f".format(balancedPortfolioToFreeCash.second)}."
    )
}

private fun printTargetPortfolio(targetPortfolio: TargetPortfolio) {
    println("Target Allocations:")
    targetPortfolio.targetAllocations.forEach { (instrument, percentage) ->
        println("$instrument, Target Percentage: ${percentage * 100}%")
    }
}

private fun printInitialPortfolio(currentPortfolio: Portfolio) {
    println("Initial Portfolio Total Value: ${currentPortfolio.calculateTotalValue()}")
    println("Current Holdings:")
    currentPortfolio.holdings.forEach { (instrument, quantity) ->
        println("$instrument, Quantity: $quantity, Value: $${"%.2f".format(quantity * instrument.currentPrice)}")
    }
}

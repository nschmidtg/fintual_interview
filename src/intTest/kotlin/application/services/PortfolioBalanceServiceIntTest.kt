package application.services

import com.nschmidtg.application.dtos.TradeOrderDto
import com.nschmidtg.application.services.PortfolioBalanceService
import com.nschmidtg.domain.FractionalUnitsStrategy
import com.nschmidtg.domain.Instrument
import com.nschmidtg.domain.MutualFund
import com.nschmidtg.domain.Portfolio
import com.nschmidtg.domain.Stock
import com.nschmidtg.domain.TargetPortfolio
import com.nschmidtg.domain.WholeUnitsStrategy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PortfolioBalanceServiceIntTest {

    @Test
    fun `integration test for balance method`() {
        val strategy = FractionalUnitsStrategy()
        val instrumentA = MutualFund(50.0, "Fondo Mutuo LarrainVial Enfoque", "ENFOQUE", strategy)
        val instrumentB = MutualFund(10.0, "Fondo Mutuo Banchile Selección Acciones Chilenas", "FMBCH", strategy)
        val holdings = hashMapOf(instrumentA as Instrument to 1.0, instrumentB as Instrument to 1.0)
        val portfolio = Portfolio(holdings)
        val targetAllocations =
            mapOf(
                instrumentA as Instrument to 0.5, // 50%
                instrumentB as Instrument to 0.5 // 50%
            )
        val targetPortfolio = TargetPortfolio(targetAllocations)
        val service = PortfolioBalanceService()

        val expected = setOf(TradeOrderDto(instrumentA, -0.4), TradeOrderDto(instrumentB, 2.0))

        // Act
        val tradeOrders: Set<TradeOrderDto> = service.balance(portfolio, targetPortfolio)

        // Assert
        assertEquals(2, tradeOrders.size)
        assertEquals(expected, tradeOrders)

        // with fractional strategy, the total value after balancing should be equal to the initial
        // total value
        assertEquals(
            expected.sumOf { it.quantity * it.instrument.currentPrice },
            0.0,
        )
    }

    @Test
    fun `integration test for balance method whole strategy`() {
        val strategy = WholeUnitsStrategy()
        val instrumentA = Stock(50.4, "Apple Inc.", "AAPL", strategy)
        val instrumentB = Stock(10.0, "Google", "GOOGL", strategy)
        val holdings = hashMapOf(instrumentA as Instrument to 10.0, instrumentB as Instrument to 5.0)
        val portfolio = Portfolio(holdings)
        val targetAllocations =
            mapOf(
                instrumentA as Instrument to 0.5, // 50%
                instrumentB as Instrument to 0.5 // 50%
            )
        val targetPortfolio = TargetPortfolio(targetAllocations)
        val service = PortfolioBalanceService()

        val expected = setOf(TradeOrderDto(instrumentA, -5.0), TradeOrderDto(instrumentB, 22.0))

        // Act
        val tradeOrders: Set<TradeOrderDto> = service.balance(portfolio, targetPortfolio)

        // Assert: check that the returned trade orders are as expected
        assertEquals(2, tradeOrders.size)
        assertEquals(expected, tradeOrders)

        // with whole strategy, the total value after balancing should be lower or equal to zero,
        // since we sell more value than we buy due to rounding in order to rebalance the portfolio
        // (no need for extra money)
        assertTrue(
            expected.sumOf { it.quantity * it.instrument.currentPrice } <= 0.0,
        )
    }

    @Test
    fun `balance when portfolio value is zero should throw`() {
        val strategy = WholeUnitsStrategy()
        val instrumentA = Stock(50.4, "Apple Inc.", "AAPL", strategy)
        val holdings = hashMapOf(instrumentA as Instrument to 0.0)
        val portfolio = Portfolio(holdings)
        val targetAllocations = mapOf(instrumentA as Instrument to 1.0) // 100%
        val targetPortfolio = TargetPortfolio(targetAllocations)
        val service = PortfolioBalanceService()

        val exception = assertThrows<RuntimeException> { service.balance(portfolio, targetPortfolio) }

        assertEquals("Cannot balance if portfolio value is zero", exception.message)
    }

    @Test
    fun `balance when target has more instruments than portfolio`() {
        val strategy = FractionalUnitsStrategy()
        val instrumentA = MutualFund(50.0, "Fondo Mutuo LarrainVial Enfoque", "ENFOQUE", strategy)
        val instrumentB = MutualFund(10.0, "Fondo Mutuo Banchile Selección Acciones Chilenas", "FMBCH", strategy)
        val instrumentC = MutualFund(20.0, "FM Fintual Very Conservative Streep", "STREEP", strategy)
        val holdings = hashMapOf(instrumentA as Instrument to 2.0)
        val portfolio = Portfolio(holdings)
        val targetAllocations =
            mapOf(
                instrumentA as Instrument to 0.4, // 40%
                instrumentB as Instrument to 0.4, // 40%
                instrumentC as Instrument to 0.2 // 20%
            )
        val targetPortfolio = TargetPortfolio(targetAllocations)
        val service = PortfolioBalanceService()

        val expected =
            setOf(TradeOrderDto(instrumentA, -1.2), TradeOrderDto(instrumentB, 4.0), TradeOrderDto(instrumentC, 1.0))

        // Act
        val tradeOrders: Set<TradeOrderDto> = service.balance(portfolio, targetPortfolio)

        // Assert
        assertEquals(3, tradeOrders.size)
        assertEquals(expected, tradeOrders)

        // with fractional strategy, the total value after balancing should be equal to the initial
        // total value
        assertEquals(
            expected.sumOf { it.quantity * it.instrument.currentPrice },
            0.0,
        )
    }
}

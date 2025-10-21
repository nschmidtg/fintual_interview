package application.services

import com.nschmidtg.application.dtos.TradeOrderDto
import com.nschmidtg.application.services.PortfolioBalanceService
import com.nschmidtg.domain.Instrument
import com.nschmidtg.domain.Portfolio
import com.nschmidtg.domain.TargetPortfolio
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PortfolioBalanceServiceTest {

    private val service = PortfolioBalanceService()

    /*
     * Simple case where 2 instruments: one instrument value 50 and another with value 10. Total 60.
     * Target is 50% each -> each instrument should have value 30.
     * Instrument 1: currently 1 unit, target 0.6 units -> sell 0.4
     * Instrument 2: currently 1 unit, target 3.0 units -> buy 2.0
     * */
    @Test
    fun balance_whenPortfolioAndTargetProvided_shouldReturnTradeOrders() {
        val instrument1 = mock(Instrument::class.java)
        val instrument2 = mock(Instrument::class.java)

        whenever(instrument1.calculateTargetQuantity(30.0)).thenReturn(Result.success(0.6))
        whenever(instrument2.calculateTargetQuantity(30.0)).thenReturn(Result.success(3.0))

        val target: TargetPortfolio = mock(TargetPortfolio::class.java)
        whenever(target.targetAllocations).thenReturn(hashMapOf(instrument1 to 0.5, instrument2 to 0.5))

        val portfolio: Portfolio = mock(Portfolio::class.java)

        whenever(portfolio.calculateTotalValue()).thenReturn(60.0)
        whenever(portfolio.getHoldingQuantityByInstrument(instrument1)).thenReturn(1.0)
        whenever(portfolio.getHoldingQuantityByInstrument(instrument2)).thenReturn(1.0)

        val expected = setOf(TradeOrderDto(instrument1, -0.4), TradeOrderDto(instrument2, 2.0))

        val actual = service.balance(portfolio, target)

        assertEquals(expected, actual)
    }

    @Test
    fun balance_whenPriceIsZero_shouldThrow() {
        val instrument1 = mock(Instrument::class.java)

        whenever(instrument1.calculateTargetQuantity(30.0))
            .thenReturn(Result.failure(RuntimeException("currentPrice = 0. Cannot compute")))

        val target: TargetPortfolio = mock(TargetPortfolio::class.java)
        whenever(target.targetAllocations).thenReturn(hashMapOf(instrument1 to 1.0))

        val portfolio: Portfolio = mock(Portfolio::class.java)

        whenever(portfolio.calculateTotalValue()).thenReturn(30.0)
        whenever(portfolio.getHoldingQuantityByInstrument(instrument1)).thenReturn(1.0)

        assertThrows<RuntimeException> { service.balance(portfolio, target) }
    }

    @Test
    fun balance_whenNoTradesNeeded_shouldReturnDtosWithZeroAmount() {
        val instrument1 = mock(Instrument::class.java)

        whenever(instrument1.calculateTargetQuantity(100.0)).thenReturn(Result.success(2.0))

        val target: TargetPortfolio = mock(TargetPortfolio::class.java)
        whenever(target.targetAllocations).thenReturn(hashMapOf(instrument1 to 1.0))

        val portfolio: Portfolio = mock(Portfolio::class.java)

        whenever(portfolio.calculateTotalValue()).thenReturn(100.0)
        whenever(portfolio.getHoldingQuantityByInstrument(instrument1)).thenReturn(2.0)

        val expected = setOf(TradeOrderDto(instrument1, 0.0))

        val actual = service.balance(portfolio, target)

        assertEquals(expected, actual)
    }

    @Test
    fun balance_whenTargetPortfolioIsEmpty_shouldThrow() {
        val target: TargetPortfolio = mock(TargetPortfolio::class.java)
        whenever(target.targetAllocations).thenReturn(emptyMap())

        val portfolio: Portfolio = mock(Portfolio::class.java)

        assertThrows<RuntimeException> { service.balance(portfolio, target) }
    }

    @Test
    fun balance_whenPortfolioValueIsZero_shouldThrow() {
        val instrument1 = mock(Instrument::class.java)

        val target: TargetPortfolio = mock(TargetPortfolio::class.java)
        whenever(target.targetAllocations).thenReturn(hashMapOf(instrument1 to 1.0))

        val portfolio: Portfolio = mock(Portfolio::class.java)

        whenever(portfolio.calculateTotalValue()).thenReturn(0.0)

        assertThrows<RuntimeException> { service.balance(portfolio, target) }
    }

    @Test
    fun balance_whenPortfolioIsEmpty_shouldThrow() {
        val instrument1 = mock(Instrument::class.java)

        val target: TargetPortfolio = mock(TargetPortfolio::class.java)
        whenever(target.targetAllocations).thenReturn(hashMapOf(instrument1 to 1.0))

        val portfolio: Portfolio = mock(Portfolio::class.java)

        whenever(portfolio.calculateTotalValue()).thenReturn(0.0)

        assertThrows<RuntimeException> { service.balance(portfolio, target) }
    }
}

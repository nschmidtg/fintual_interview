package application.services

import com.nschmidtg.application.dtos.TradeOrderDto
import com.nschmidtg.application.services.TradingService
import com.nschmidtg.domain.Instrument
import com.nschmidtg.domain.Portfolio
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class TradingServiceTest {

    private val service = TradingService()

    @Test
    fun processTradeOperations_shouldReturnCorrectResults() {
        val instrument1 = mock(Instrument::class.java)
        val instrument2 = mock(Instrument::class.java)

        val tradeOrders = setOf(TradeOrderDto(instrument1, 2.0), TradeOrderDto(instrument2, -1.0))

        val initialPortfolio = Portfolio(hashMapOf(instrument1 to 1.0, instrument2 to 3.0))

        whenever(instrument1.currentPrice).thenReturn(1.0)
        whenever(instrument2.currentPrice).thenReturn(2.0)

        val (newPortfolio, freeCash) = service.processTradeOperations(tradeOrders, initialPortfolio)

        assertEquals(3.0, newPortfolio.getHoldingQuantityByInstrument(instrument1))
        assertEquals(2.0, newPortfolio.getHoldingQuantityByInstrument(instrument2))
        assertEquals(0.0, freeCash)
    }
}

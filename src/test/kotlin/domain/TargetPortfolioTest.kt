package domain

import com.nschmidtg.domain.Instrument
import com.nschmidtg.domain.TargetPortfolio
import kotlin.test.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock

class TargetPortfolioTest {
    @Test
    fun shouldThrowException_whenPortfolioTotalPercentagesNot100() {
        val instrument1: Instrument = mock(Instrument::class.java)
        val instrument2: Instrument = mock(Instrument::class.java)
        val holdings = hashMapOf(instrument1 to 0.6, instrument2 to 0.39)
        assertThrows(IllegalArgumentException::class.java) { TargetPortfolio(holdings) }
    }

    @Test
    fun shouldCreateTargetPortfolio_whenPortfolioTotalPercentagesIs100() {
        val instrument1: Instrument = mock(Instrument::class.java)
        val instrument2: Instrument = mock(Instrument::class.java)
        val holdings = hashMapOf(instrument1 to 0.6, instrument2 to 0.4)
        val targetPortfolio = TargetPortfolio(holdings)
        assertNotNull(targetPortfolio)
    }

    @Test
    fun shouldThrowException_whenOneInstrumentPercentageIsNegative() {
        val instrument2: Instrument = mock(Instrument::class.java)
        val holdings = hashMapOf(instrument2 to -0.1)
        assertThrows(IllegalArgumentException::class.java) { TargetPortfolio(holdings) }
    }

    @Test
    fun shouldThrowException_whenOneInstrumentPercentageIsOver100() {
        val instrument2: Instrument = mock(Instrument::class.java)
        val holdings = hashMapOf(instrument2 to 1.1)
        assertThrows(IllegalArgumentException::class.java) { TargetPortfolio(holdings) }
    }
}

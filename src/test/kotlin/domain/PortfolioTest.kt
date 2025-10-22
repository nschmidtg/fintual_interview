package domain

import com.nschmidtg.domain.Instrument
import com.nschmidtg.domain.Portfolio
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PortfolioTest {

    @Test
    fun calculateTotalValue_whenSingleEntry_shouldReturnCorrectValue() {

        val instrument = mock(Instrument::class.java)
        val holdings = hashMapOf(instrument to STORE_INPUT_QUANTITY_1)
        val portfolio = Portfolio(holdings)
        val expected = 150.0

        whenever(instrument.currentPrice).thenReturn(STORE_INPUT_PRICE_1)

        val actual = portfolio.calculateTotalValue()

        assertEquals(expected, actual)
    }

    @Test
    fun calculateTotalValue_whenMultipleEntroes_shouldReturnCorrectValue() {

        val instrument1 = mock(Instrument::class.java)
        val instrument2 = mock(Instrument::class.java)
        val holdings = hashMapOf(instrument1 to STORE_INPUT_QUANTITY_1, instrument2 to STORE_INPUT_QUANTITY_2)
        val portfolio = Portfolio(holdings)
        val expected = 400.0

        whenever(instrument1.currentPrice).thenReturn(STORE_INPUT_PRICE_1)
        whenever(instrument2.currentPrice).thenReturn(STORE_INPUT_PRICE_2)

        val actual = portfolio.calculateTotalValue()

        assertEquals(expected, actual)
    }

    @Test
    fun getHoldingQuantityByInstrument_whenInstrumentExists_shouldReturnCorrectQuantity() {
        val instrument = mock(Instrument::class.java)
        val holdings = hashMapOf(instrument to STORE_INPUT_QUANTITY_1)
        val portfolio = Portfolio(holdings)
        val expected = STORE_INPUT_QUANTITY_1

        val actual = portfolio.getHoldingQuantityByInstrument(instrument)

        assertEquals(expected, actual)
    }

    @Test
    fun getHoldingQuantityByInstrument_whenInstrumentDoesNotExist_shouldReturnZero() {
        val instrumentStored = mock(Instrument::class.java)
        val instrumentQueried = mock(Instrument::class.java)
        val holdings = hashMapOf(instrumentStored to STORE_INPUT_QUANTITY_1)
        val portfolio = Portfolio(holdings)
        val expected = 0.0

        val actual = portfolio.getHoldingQuantityByInstrument(instrumentQueried)

        assertEquals(expected, actual)
    }

    companion object {
        private const val STORE_INPUT_PRICE_1 = 15.0
        private const val STORE_INPUT_QUANTITY_1 = 10.0
        private const val STORE_INPUT_PRICE_2 = 25.0
        private const val STORE_INPUT_QUANTITY_2 = 10.0
    }
}

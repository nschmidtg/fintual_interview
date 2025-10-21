package domain

import com.nschmidtg.domain.Stock
import com.nschmidtg.domain.WholeUnitsStrategy
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class StockTest {

    val wholeUnitsStrategy: WholeUnitsStrategy = mock(WholeUnitsStrategy::class.java)

    @Test
    fun calculateTargetQuantity_shouldInvokeWholeUnitsStrategy() {
        val inputTargetValue = 1050.0
        val inputCurrentPrice = 200.0
        val expected = 5.25
        whenever(wholeUnitsStrategy.calculateTargetQuantity(inputTargetValue, inputCurrentPrice))
            .thenReturn(Result.success(expected))

        val stock = Stock(inputCurrentPrice, "Test Stock", "TST", wholeUnitsStrategy)

        val result = stock.calculateTargetQuantity(inputTargetValue)

        assertEquals(true, result.isSuccess)
        assertEquals(expected, result.getOrThrow())

        verify(wholeUnitsStrategy).calculateTargetQuantity(inputTargetValue, inputCurrentPrice)
    }
}

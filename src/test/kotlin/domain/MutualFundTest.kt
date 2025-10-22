package domain

import com.nschmidtg.domain.FractionalUnitsStrategy
import com.nschmidtg.domain.MutualFund
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MutualFundTest {

    val fractionalUnitsStrategy: FractionalUnitsStrategy = mock(FractionalUnitsStrategy::class.java)

    @Test
    fun calculateTargetQuantity_shouldInvokeFractionalUnitsStrategy() {
        val inputTargetValue = 1050.0
        val inputCurrentPrice = 200.0
        val expected = 5.25
        whenever(fractionalUnitsStrategy.calculateTargetQuantity(inputTargetValue, inputCurrentPrice))
            .thenReturn(Result.success(expected))

        val stock = MutualFund(inputCurrentPrice, "Test Crypto", "TST", fractionalUnitsStrategy)

        val result = stock.calculateTargetQuantity(inputTargetValue)

        assertEquals(true, result.isSuccess)
        assertEquals(expected, result.getOrThrow())

        verify(fractionalUnitsStrategy).calculateTargetQuantity(inputTargetValue, inputCurrentPrice)
    }

    @Test
    fun toString_shouldReturnCorrectFormat() {
        val mutual = MutualFund(150.0, "Test MutualFund", "MTM", fractionalUnitsStrategy)
        val expected = "MutualFund(MTM)"

        val actual = mutual.toString()

        assertEquals(expected, actual)
    }
}

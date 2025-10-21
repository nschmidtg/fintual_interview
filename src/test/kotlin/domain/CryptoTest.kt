package domain

import com.nschmidtg.domain.Crypto
import com.nschmidtg.domain.FractionalUnitsStrategy
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CryptoTest {

    val fractionalUnitsStrategy: FractionalUnitsStrategy = mock(FractionalUnitsStrategy::class.java)

    @Test
    fun calculateTargetQuantity_shouldInvokeFractionalUnitsStrategy() {
        val inputTargetValue = 1050.0
        val inputCurrentPrice = 200.0
        val expected = 5.25
        whenever(fractionalUnitsStrategy.calculateTargetQuantity(inputTargetValue, inputCurrentPrice))
            .thenReturn(Result.success(expected))

        val stock = Crypto(inputCurrentPrice, "Test Crypto", "TST", fractionalUnitsStrategy)

        val result = stock.calculateTargetQuantity(inputTargetValue)

        assertEquals(true, result.isSuccess)
        assertEquals(expected, result.getOrThrow())

        verify(fractionalUnitsStrategy).calculateTargetQuantity(inputTargetValue, inputCurrentPrice)
    }
}

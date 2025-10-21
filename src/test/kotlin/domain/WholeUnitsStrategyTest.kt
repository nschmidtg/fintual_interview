package domain

import com.nschmidtg.domain.WholeUnitsStrategy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class WholeUnitsStrategyTest() {

    @Test
    fun calculateTargetQuantity_shouldReturnWholeUnits() {
        // Arrange
        val strategy = WholeUnitsStrategy()
        val targetValue = 1050.0
        val price = 200.0

        val expected = 5.0

        // Act
        val actual = strategy.calculateTargetQuantity(targetValue, price)

        // Assert
        assertEquals(expected, actual.getOrThrow())
    }
}

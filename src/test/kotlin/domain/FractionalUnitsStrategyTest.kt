package domain

import com.nschmidtg.domain.FractionalUnitsStrategy
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class FractionalUnitsStrategyTest {

    @Test
    fun calculateTargetQuantity_shouldReturnWholeUnits() {
        // Arrange
        val strategy = FractionalUnitsStrategy()
        val targetValue = 1050.0
        val price = 200.0

        val expected = 5.25

        // Act
        val actual = strategy.calculateTargetQuantity(targetValue, price)

        // Assert
        assertEquals(expected, actual.getOrThrow())
    }

    @Test
    fun calculateTargetQuantity_shouldReturnFractionalUnitsWhenTargetValueLessThanPrice() {
        // Arrange
        val strategy = FractionalUnitsStrategy()
        val targetValue = 150.0
        val price = 200.0

        val expected = 0.75

        // Act
        val actual = strategy.calculateTargetQuantity(targetValue, price)

        // Assert
        assertEquals(expected, actual.getOrThrow())
    }

    @Test
    fun calculateTargetQuantity_shouldThrowWhenPriceIsZero() {
        // Arrange
        val strategy = FractionalUnitsStrategy()
        val targetValue = 150.0
        val price = 0.0

        // Act
        val actual = strategy.calculateTargetQuantity(targetValue, price)

        // Assert
        assertEquals(true, actual.isFailure)
    }
}

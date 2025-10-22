package cinema

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SeatsServiceTest {
    @Test
    fun `income zero`() {
        val sut = createSut()

        val result = sut.getCurrentIncome()

        assertEquals(0, result)
    }

    @Test
    fun `income positive`() {
        val sut = createSut()
        sut.purchaseSeatIn(1, 1)
        sut.purchaseSeatIn(5, 2)

        val result = sut.getCurrentIncome()

        assertTrue(result > 0)
    }

    @Test
    fun `available seats number after purchasing two`() {
        val sut = createSut()
        sut.purchaseSeatIn(1, 1)
        sut.purchaseSeatIn(1, 2)

        val result = sut.getNumberOfAvailableSeats()

        val rows = sut.getNumOfRows()
        val cols = sut.getNumOfColumns()
        val expected = rows * cols - 2
        assertEquals(expected, result)
    }

    @Test
    fun `available seats number is zero`() {
        val sut = createSut()
        val rows = sut.getNumOfRows()
        val cols = sut.getNumOfColumns()
        (1..rows).forEach { row ->
            (1..cols).forEach { col ->
                sut.purchaseSeatIn(row, col)
            }
        }

        val result = sut.getNumberOfAvailableSeats()

        assertEquals(0, result)
    }

    @Test
    fun `purchased tickets number is two`() {
        val sut = createSut()
        sut.purchaseSeatIn(1, 1)
        sut.purchaseSeatIn(1, 2)

        val result = sut.getNumberOfPurchasedTickets()

        assertEquals(2, result)
    }

    @Test
    fun `purchased tickets number is zero`() {
        val sut = createSut()

        val result = sut.getNumberOfPurchasedTickets()

        assertEquals(0, result)
    }
}

private fun createSut(): SeatsService = SeatsService(TokenService())
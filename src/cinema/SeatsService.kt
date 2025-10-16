package cinema

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

private const val NUM_OF_COLUMNS = 9
private const val NUM_OF_ROWS = NUM_OF_COLUMNS

@Service
class SeatsService {
    private val seats: ConcurrentMap<Int, Seat> = ConcurrentHashMap()

    init {
        (1..NUM_OF_ROWS).forEach { row ->
            (1..NUM_OF_COLUMNS).forEach { column ->
                val price = if (row <= 4) 10 else 8
                val id = computeSeatId(row, column)
                val seat = Seat(
                    row = row,
                    column = column,
                    price = price,
                    isTaken = false
                )
                seats.put(id, seat)
            }
        }
    }

    fun getAllSeats(): Set<Seat> = seats.values.toSet()

    fun getNumOfColumns(): Int = NUM_OF_COLUMNS

    fun getNumOfRows(): Int = NUM_OF_ROWS

    fun purchaseSeatIn(
        row: Int,
        column: Int
    ): Seat? {
        val seat = getSeatIn(row = row, column = column)

        return if (seat.isTaken) {
            null
        } else {
            seat.reserve()
            seat
        }
    }

    private fun getSeatIn(
        row: Int,
        column: Int
    ): Seat {
        val id = computeSeatId(row = row, column = column)
        val seat = seats[id]

        check(seat != null) { "The number of a row or a column is out of bounds!" }

        return seat
    }

    private fun computeSeatId(row: Int, column: Int) = row * 10 + column
}

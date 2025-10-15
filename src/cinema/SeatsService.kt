package cinema

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

private const val NUM_OF_COLUMNS = 9
private const val NUM_OF_ROWS = NUM_OF_COLUMNS

@Service
class SeatsService {
    private val seatsNew: ConcurrentMap<Int, Seat> = ConcurrentHashMap()

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
                seatsNew.put(id, seat)
            }
        }
    }

    fun getAllSeats(): Set<Seat> = seatsNew.values.toSet()

    fun getNumOfColumns(): Int = NUM_OF_COLUMNS

    fun getNumOfRows(): Int = NUM_OF_ROWS

    fun purchaseSeat(row: Int, column: Int): SeatDto {
        TODO("Not implemented yet")
    }

    private fun computeSeatId(row: Int, column: Int) = row * 10 + column
}

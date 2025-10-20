package cinema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

private const val NUM_OF_COLUMNS = 9
private const val NUM_OF_ROWS = NUM_OF_COLUMNS

@Service
class SeatsService @Autowired constructor(tokenService: TokenService) {
    private val seats: ConcurrentMap<UUID, Seat> = ConcurrentHashMap()

    init {
        (1..NUM_OF_ROWS).forEach { row ->
            (1..NUM_OF_COLUMNS).forEach { column ->
                val price = if (row <= 4) 10 else 8
                val id: UUID = tokenService.generateToken()
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
    ): Seat {
        val seat = getSeatIn(row = row, column = column)

        check(!seat.isTaken) { "The ticket has been already purchased!" }

        seat.reserve()

        return seat
    }

    private fun getSeatIn(
        row: Int,
        column: Int
    ): Seat {
        val seat: Seat? = seats.values.asSequence().filter { seat ->
            seat.row == row && seat.column == column
        }.firstOrNull()

        checkNotNull(seat) { "The number of a row or a column is out of bounds!" }

        return seat
    }
}

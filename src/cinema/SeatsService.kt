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
    ): Pair<UUID, Seat> {
        val idToSeat: Pair<UUID, Seat> = getSeatIn(row = row, column = column)

        check(!idToSeat.second.isTaken) { "The ticket has been already purchased!" }

        idToSeat.second.reserve()

        return idToSeat
    }

    private fun getSeatIn(
        row: Int,
        column: Int
    ): Pair<UUID, Seat> {
        val idToSeat = seats.asSequence().filter { (_, seat) ->
            seat.row == row && seat.column == column
        }.map { (uuid, seat) -> uuid to seat }
            .firstOrNull()

        checkNotNull(idToSeat) { "The number of a row or a column is out of bounds!" }

        return idToSeat
    }
}

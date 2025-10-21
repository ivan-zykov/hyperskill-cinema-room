package cinema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

private const val NUM_OF_COLUMNS = 9
private const val NUM_OF_ROWS = NUM_OF_COLUMNS
private const val WRONG_ROW_COL_MESSAGE = "The number of a row or a column is out of bounds!"
private const val TICKET_ALREADY_BOOKED_MESSAGE = "The ticket has been already purchased!"
private const val WRONG_TOKEN_MESSAGE = "Wrong token!"

@Service
class SeatsService @Autowired constructor(val tokenService: TokenService) {
    private val seats: ConcurrentMap<Int, Seat> = ConcurrentHashMap()

    init {
        (1..NUM_OF_ROWS).forEach { row ->
            (1..NUM_OF_COLUMNS).forEach { column ->
                val index: Int = computeSeatIndex(row, column)
                val price: Int = if (row <= 4) 10 else 8
                val seat = Seat(
                    row = row,
                    column = column,
                    price = price,
                )
                seats.put(index, seat)
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
        val seat: Seat = getSeatIn(row = row, column = column)

        check(!seat.isTaken) { TICKET_ALREADY_BOOKED_MESSAGE }

        val token: UUID = tokenService.generateToken()
        seat.reserveWith(token)

        return seat
    }

    fun returnSeatWith(token: String): Seat {
        val tokenParsed: UUID = try {
            UUID.fromString(token)
        } catch (_: IllegalArgumentException) {
            throw InvalidTokenException(WRONG_TOKEN_MESSAGE)
        }

        val seat: Seat = getSeatWith(tokenParsed)

        seat.cancelReservation()

        return seat
    }

    private fun getSeatIn(
        row: Int,
        column: Int
    ): Seat {
        val seatIndex = computeSeatIndex(row = row, column = column)

        val seat: Seat? = seats[seatIndex]
        checkNotNull(seat) { WRONG_ROW_COL_MESSAGE }

        return seat
    }

    private fun getSeatWith(token: UUID): Seat {
        val seat = seats.asSequence().filter { (_, seat) ->
            seat.token == token
        }.firstOrNull()?.value

        checkNotNull(seat) { WRONG_TOKEN_MESSAGE }

        return seat
    }

    fun getCurrentIncome(): Int {
        TODO("Not yet implemented")
    }

    fun getNumberOfAvailableSeats(): Int {
        TODO("Not yet implemented")
    }

    fun getNumberOfPurchasedTickets(): Int {
        TODO("Not yet implemented")
    }
}

class InvalidTokenException(message: String) : RuntimeException(message)

private fun computeSeatIndex(row: Int, column: Int) = row * 10 + column
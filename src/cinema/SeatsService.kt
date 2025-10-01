package cinema

import org.springframework.stereotype.Service

private const val NUM_OF_COLUMNS = 9
private const val NUM_OF_ROWS = NUM_OF_COLUMNS

typealias IsTaken = Boolean

@Service
class SeatsService {
    private val seats: MutableMap<Seat, IsTaken> = buildMap {
        (1..NUM_OF_ROWS).forEach { row ->
            (1..NUM_OF_COLUMNS).forEach { column ->
                val price = if (row <= 4) {
                    10
                } else {
                    8
                }

                put(
                    Seat(
                        row = row,
                        column = column,
                        price = price,
                    ),
                    false
                )
            }
        }
    }
        .toMutableMap()

    fun getAllSeats(): SeatsAvailable {
        val seats: Set<Seat> = seats.keys

        return SeatsAvailable(
            totalRows = NUM_OF_ROWS,
            totalColumns = NUM_OF_COLUMNS,
            availableSeats = seats,
        )
    }

    fun purchaseSeat(row: Int, column: Int): Seat {
        TODO("Not implemented yet")
    }
}

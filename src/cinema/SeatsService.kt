package cinema

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

private const val NUM_OF_COLUMNS = 9
private const val NUM_OF_ROWS = NUM_OF_COLUMNS

@Service
class SeatsService {
    private val seats: ConcurrentMap<SeatLocation, SeatAttributes> = ConcurrentHashMap()

    init {
        (1..NUM_OF_ROWS).forEach { row ->
            (1..NUM_OF_COLUMNS).forEach { column ->
                val price = if (row <= 4) 10 else 8
                val location = SeatLocation(row, column)
                val attributes = SeatAttributes(price, false)
                seats.put(location, attributes)
            }
        }
    }

    fun getAllSeats(): SeatsAvailableDto {
        val seats: Set<SeatDto> = buildSet {
            seats.forEach { (location, attributes) ->
                add(
                    SeatDto(
                        row = location.row,
                        column = location.column,
                        price = attributes.price
                    )
                )
            }
        }

        return SeatsAvailableDto(
            totalRows = NUM_OF_ROWS,
            totalColumns = NUM_OF_COLUMNS,
            availableSeats = seats,
        )
    }

    fun purchaseSeat(row: Int, column: Int): SeatDto {
        TODO("Not implemented yet")
    }
}

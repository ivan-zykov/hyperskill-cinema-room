package cinema

import org.springframework.stereotype.Service

private const val NUM_OF_COLUMNS = 9
private const val NUM_OF_ROWS = NUM_OF_COLUMNS

@Service
class CinemaService {
    fun getSeats(): SeatsAvailable {
        val seats: List<Seat> = buildList {
            (1..NUM_OF_ROWS).forEach { row ->
                (1..NUM_OF_COLUMNS).forEach { column ->
                    add(
                        Seat(
                            row = row,
                            column = column
                        )
                    )
                }
            }
        }

        return SeatsAvailable(
            totalRows = NUM_OF_ROWS,
            totalColumns = NUM_OF_COLUMNS,
            availableSeats = seats
        )
    }
}

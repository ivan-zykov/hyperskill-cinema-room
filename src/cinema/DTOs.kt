package cinema

import com.fasterxml.jackson.annotation.JsonProperty

@Suppress("unused")
class SeatOutDto(
    val row: Int,
    val column: Int,
    val price: Int,
)

@Suppress("unused")
class SeatsAvailableDto(
    @JsonProperty("total_rows")
    val totalRows: Int,

    @JsonProperty("total_columns")
    val totalColumns: Int,

    @JsonProperty("available_seats")
    val availableSeats: Set<SeatOutDto>,
) {
    companion object {
        fun create(
            numRows: Int,
            numCols: Int,
            seatsDto: Set<SeatOutDto>
        ): SeatsAvailableDto = SeatsAvailableDto(
            totalRows = numRows,
            totalColumns = numCols,
            availableSeats = seatsDto
        )
    }
}

data class SeatInDto(
    val row: Int,
    val column: Int,
)

@Suppress("unused")
class OrderOutDto(
    val token: String,
    val ticket: SeatOutDto,
)

@Suppress("unused")
class OrderInDTO(val token: String)

@Suppress("unused")
class ReturnedOrderDto(
    @JsonProperty("returned_ticket")
    val returnedTicket: SeatOutDto,
)
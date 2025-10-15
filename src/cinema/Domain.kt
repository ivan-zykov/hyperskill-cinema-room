package cinema

import com.fasterxml.jackson.annotation.JsonProperty

class SeatsAvailableDto(
    @JsonProperty("total_rows")
    val totalRows: Int,

    @JsonProperty("total_columns")
    val totalColumns: Int,

    @JsonProperty("available_seats")
    val availableSeats: Set<SeatDto>,
)

class SeatDto(
    val row: Int,
    val column: Int,
    val price: Int,
)

class SeatAttributes(
    val price: Int,
    val isTaken: Boolean,
)

data class SeatLocation(
    val row: Int,
    val column: Int,
)

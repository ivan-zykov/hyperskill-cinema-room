package cinema

import com.fasterxml.jackson.annotation.JsonProperty

class SeatsAvailable(
    @JsonProperty("total_rows")
    val totalRows: Int,

    @JsonProperty("total_columns")
    val totalColumns: Int,

    @JsonProperty("available_seats")
    val availableSeats: List<Seat>,
)

data class Seat(
    val row: Int,
    val column: Int,
)

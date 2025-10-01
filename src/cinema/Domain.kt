package cinema

import com.fasterxml.jackson.annotation.JsonProperty

class SeatsAvailable(
    @JsonProperty("total_rows")
    val totalRows: Int,

    @JsonProperty("total_columns")
    val totalColumns: Int,

    @JsonProperty("available_seats")
    val availableSeats: Set<Seat>,
)

data class Seat(
    val row: Int,
    val column: Int,
    val price: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Seat

        if (row != other.row) return false
        if (column != other.column) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + column
        return result
    }
}

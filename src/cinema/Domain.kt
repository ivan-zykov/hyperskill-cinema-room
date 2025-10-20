package cinema

import java.util.*

data class Seat(
    val row: Int,
    val column: Int,
    val price: Int,
    var isTaken: Boolean = false,
    var token: UUID? = null,
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

    fun reserveWith(token: UUID) {
        isTaken = true
        this.token = token
    }

    fun cancelReservation() {
        isTaken = false
        token = null
    }
}
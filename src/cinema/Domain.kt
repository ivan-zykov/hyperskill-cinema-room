package cinema

import java.util.*

class Seat(
    val row: Int,
    val column: Int,
    val price: Int,
    var isTaken: Boolean = false,
    var token: UUID? = null,
) {
    fun reserveWith(token: UUID) {
        isTaken = true
        this.token = token
    }

    fun cancelReservation() {
        isTaken = false
        token = null
    }
}
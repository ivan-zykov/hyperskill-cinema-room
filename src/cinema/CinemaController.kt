package cinema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Suppress("unused")
@RestController
class CinemaController @Autowired constructor(
    val seatsService: SeatsService,
    val authService: AuthService,
) {
    @GetMapping("/health")
    fun health(): ResponseEntity<String> = ResponseEntity("", HttpStatus.OK)

    @GetMapping("/seats")
    fun seats(): ResponseEntity<SeatsAvailableDto> {
        val seatsDto = seatsService.getAllSeats().toDto()
        val numRows = seatsService.getNumOfRows()
        val numCols = seatsService.getNumOfColumns()
        val seatsAvailable = SeatsAvailableDto.create(
            numRows = numRows,
            numCols = numCols,
            seatsDto = seatsDto
        )

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(seatsAvailable)
    }

    @PostMapping("/purchase")
    fun purchaseSeat(@RequestBody selectedSeat: SeatInDto): ResponseEntity<OrderOutDto> {
        val (row, column) = selectedSeat

        val purchasedSeat: Seat = seatsService.purchaseSeatIn(row = row, column = column)

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                OrderOutDto(
                    token = purchasedSeat.token.toString(),
                    ticket = purchasedSeat.toDto()
                )
            )
    }

    @PostMapping("/return")
    fun returnSeat(@RequestBody order: OrderInDTO): ResponseEntity<ReturnedOrderDto> {
        val token: String = order.token

        val returnedTicket: Seat = seatsService.returnSeatWith(token)

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                ReturnedOrderDto(
                    returnedTicket = returnedTicket.toDto()
                )
            )
    }

    @GetMapping("/stats")
    fun getStats(@RequestParam password: String): ResponseEntity<StatsDto> {
        val isValidUser = authService.authenticateFor(password)

        if (!isValidUser) {
            throw WrongPasswordException("The password is wrong!")
        }

        val currentIncome: Int = seatsService.getCurrentIncome()
        val numberOfAvailableSeats: Int = seatsService.getNumberOfAvailableSeats()
        val numberOfPurchasedTickets: Int = seatsService.getNumberOfPurchasedTickets()

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                StatsDto(
                    currentIncome = currentIncome,
                    numberOfAvailableSeats = numberOfAvailableSeats,
                    numberOfPurchasedTickets = numberOfPurchasedTickets
                )
            )
    }
}

class WrongPasswordException(message: String) : RuntimeException(message)

private fun Set<Seat>.toDto(): Set<SeatOutDto> = buildSet {
    this@toDto.forEach { seat ->
        add(
            seat.toDto()
        )
    }
}

private fun Seat.toDto() = SeatOutDto(
    row = row,
    column = column,
    price = price
)
package cinema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Suppress("unused")
@RestController
class CinemaController @Autowired constructor(
    val seatsService: SeatsService,
    val tokenService: TokenService
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
    fun purchaseSeat(@RequestBody selectedSeat: SeatInDto): ResponseEntity<OrderDto> {
        val (row, column) = selectedSeat

        val purchasedSeat: Seat? = seatsService.purchaseSeatIn(row = row, column = column)
        checkNotNull(purchasedSeat) { "The ticket has been already purchased!" }

        val token: UUID = tokenService.generateToken()

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                OrderDto(
                    token = token.toString(),
                    ticket = purchasedSeat.toDto()
                )
            )
    }
}

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
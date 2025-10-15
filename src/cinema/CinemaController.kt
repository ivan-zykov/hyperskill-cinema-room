package cinema

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Suppress("unused")
@RestController
class CinemaController @Autowired constructor(val seatsService: SeatsService) {

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
    fun purchaseSeat(
        @RequestParam row: Int,
        @RequestParam column: Int,
    ): ResponseEntity<SeatDto> {
        val purchasedSeat = seatsService.purchaseSeat(row, column)

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(purchasedSeat)
    }
}

private fun Set<Seat>.toDto(): Set<SeatDto> = buildSet {
    this@toDto.forEach { seat ->
        add(
            seat.toDto()
        )
    }
}

private fun Seat.toDto() = SeatDto(
    row = row,
    column = column,
    price = price
)


@Suppress("unused")
class SeatDto(
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
    val availableSeats: Set<SeatDto>,
) {
    companion object {
        fun create(
            numRows: Int,
            numCols: Int,
            seatsDto: Set<SeatDto>
        ): SeatsAvailableDto = SeatsAvailableDto(
            totalRows = numRows,
            totalColumns = numCols,
            availableSeats = seatsDto
        )
    }
}
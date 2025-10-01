package cinema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CinemaController @Autowired constructor(val seatsService: SeatsService) {

    @GetMapping("/health")
    fun health(): ResponseEntity<String> = ResponseEntity("", HttpStatus.OK)

    @GetMapping("/seats")
    fun seats(): ResponseEntity<SeatsAvailable> {
        val seats = seatsService.getAllSeats()

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(seats)
    }

    @PostMapping("/purchase")
    fun purchaseSeat(
        @RequestParam row: Int,
        @RequestParam column: Int,
    ): ResponseEntity<Seat> {
        val purchasedSeat = seatsService.purchaseSeat(row, column)

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(purchasedSeat)
    }
}
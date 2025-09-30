package cinema

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CinemaController {

    @GetMapping("/health")
    fun health(): ResponseEntity<String> = ResponseEntity("", HttpStatus.OK)
}
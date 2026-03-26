package net.ivanvzykov.cinema.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import net.ivanvzykov.cinema.SecurityConfig
import net.ivanvzykov.cinema.businessandrepo.Seat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest
@Import(SecurityConfig::class) // Required for Spring Auth to work correctly
class CinemaControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var seatsService: SeatsService

    @Test
    fun `List all seats`() {
        val seats = setOf(
            Seat(row = 1, column = 1, price = 20, isTaken = false),
            Seat(row = 1, column = 2, price = 40, isTaken = true)
        )
        every { seatsService.getAllSeats() } returns seats
        every { seatsService.getNumOfRows() } returns 1
        every { seatsService.getNumOfColumns() } returns 2

        mockMvc.get("/seats")
            .andExpectAll {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.total_rows") { value(1) }
                jsonPath("$.total_columns") { value(2) }
                jsonPath("$.available_seats[0].row") { value(1) }
                jsonPath("$.available_seats[0].column") { value(1) }
                jsonPath("$.available_seats[0].price") { value(20) }
                jsonPath("$.available_seats[1].row") { value(1) }
                jsonPath("$.available_seats[1].column") { value(2) }
                jsonPath("$.available_seats[1].price") { value(40) }
            }
    }

}

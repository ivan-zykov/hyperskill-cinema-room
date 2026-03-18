package net.ivanvzykov.cinema.presentation

import net.ivanvzykov.cinema.businessandrepo.Seat
import net.ivanvzykov.cinema.businessandrepo.Stats

interface SeatsService {
    fun getAllSeats(): Set<Seat>
    fun getNumOfColumns(): Int
    fun getNumOfRows(): Int
    fun purchaseSeatIn(row: Int, column: Int): Seat
    fun returnSeatWith(token: String): Seat
    fun getStats(): Stats
}

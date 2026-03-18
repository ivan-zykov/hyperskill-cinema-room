package net.ivanvzykov.cinema.presentation

interface AuthService {
    fun authenticateFor(password: String): Boolean
}
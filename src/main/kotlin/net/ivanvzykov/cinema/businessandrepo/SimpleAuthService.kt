package net.ivanvzykov.cinema.businessandrepo

import net.ivanvzykov.cinema.presentation.AuthService
import org.springframework.stereotype.Service

private const val CORRECT_PASS = "super_secret"

@Service
class SimpleAuthService : AuthService {
    override fun authenticateFor(password: String): Boolean = password == CORRECT_PASS
}

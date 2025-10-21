package cinema

import org.springframework.stereotype.Service

private const val CORRECT_PASS = "super_secret"

@Service
class AuthService {
    fun authenticateFor(password: String): Boolean = password == CORRECT_PASS
}

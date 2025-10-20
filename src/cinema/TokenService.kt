package cinema

import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {
    fun generateToken(): UUID = UUID.randomUUID()
}
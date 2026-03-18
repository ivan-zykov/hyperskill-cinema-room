package net.ivanvzykov.cinema.businessandrepo

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TokenService {
    fun generateToken(): UUID = UUID.randomUUID()
}
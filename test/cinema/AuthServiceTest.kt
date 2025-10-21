package cinema

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class AuthServiceTest {
    @Test
    fun `pass auth`() {
        val sut = AuthService()
        val password = "super_secret"

        val result = sut.authenticateFor(password)

        assertTrue(result)
    }

    @TestFactory
    fun `fail auth`() = listOf(
        "",
        "a",
        "1",
    ).map { pass ->
        dynamicTest("with password: $pass") {
            val sut = AuthService()

            val result = sut.authenticateFor(pass)

            assertFalse(result)
        }
    }
}
package cinema

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Suppress("unused")
@ControllerAdvice
class ControllerExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalArgument(
        ex: IllegalStateException,
    ): ResponseEntity<Map<String, String>> {
        val body = mapOf(
            "error" to (ex.message ?: "")
        )

        return ResponseEntity<Map<String, String>>(body, HttpStatus.BAD_REQUEST)
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<in Any>? {
        val body = mapOf("error" to ex.message)

        return ResponseEntity(body, headers, HttpStatus.BAD_REQUEST)
    }
}
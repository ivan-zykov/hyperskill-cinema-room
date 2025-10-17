package cinema

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

typealias ErrorBody = Map<String, String>

@Suppress("unused")
@ControllerAdvice
class ControllerExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(ex: IllegalStateException): ResponseEntity<ErrorBody> {
        val body = makeErrorBodyFor(ex.message)

        return ResponseEntity<ErrorBody>(body, HttpStatus.BAD_REQUEST)
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val body = makeErrorBodyFor(ex.message)

        return ResponseEntity(body, headers, HttpStatus.BAD_REQUEST)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val body = makeErrorBodyFor(ex.message)

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}

private fun makeErrorBodyFor(message: String?): ErrorBody = mapOf(
    "error" to (message ?: "")
)
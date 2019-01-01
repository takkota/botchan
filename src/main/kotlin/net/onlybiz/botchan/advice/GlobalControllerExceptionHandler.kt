package net.onlybiz.botchan.advice

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice  // Makes this the default behaviour of all controllers
@ConditionalOnProperty(prefix = "app", name = arrayOf("disable-default-exception-handling"))
class GlobalControllerExceptionHandler {
    @ExceptionHandler(Exception::class)  // Catch any exception
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // Returns an error code 500
    fun handleException() {
    }
}
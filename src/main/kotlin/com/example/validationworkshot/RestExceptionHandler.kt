package com.example.validationworkshot

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

data class Errors(
    val errors: List<String>
)

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(exception: MethodArgumentNotValidException): Errors {
        return Errors(exception.fieldErrors.mapNotNull { it.defaultMessage })
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(exception: ConstraintViolationException): Errors {
        return Errors(exception.constraintViolations?.mapNotNull { it.message } ?: emptyList())
    }
}
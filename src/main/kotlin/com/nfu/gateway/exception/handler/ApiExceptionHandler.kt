package com.nfu.gateway.exception.handler

import com.nfu.gateway.exception.ApiException
import com.nfu.gateway.exception.response.ApiErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import reactor.core.publisher.Mono

@RestControllerAdvice
class ApiExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(ApiException::class)
    fun handle(ex: ApiException): Mono<ResponseEntity<Any>> {
        return Mono.just(
            ResponseEntity(
                ApiErrorResponse(ex.apiError),
                HttpStatus.resolve(ex.apiError.status)!!
            )
        )
    }
}
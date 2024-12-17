package com.nfu.gateway.exception.constant

import org.springframework.http.HttpStatus

enum class GatewayError(
    val status: Int,
    val resultCode: String,
    val message: String
) {

    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS.value(), "-97", "Too Many Requests"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "-98", "Unauthorized"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "-99", "Internal Server Error"),

    CAN_NOT_CONNECT_TO_SERVICE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "-100", "Cannot Connect To Service"),
    ;
}
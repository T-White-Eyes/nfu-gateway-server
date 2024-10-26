package com.nfu.gateway.exception.constant

import org.springframework.http.HttpStatus

enum class ApiError(
    val status: Int,
    val resultCode: String,
    val message: String
) {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "-98", "Unauthorized"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "-99", "Internal Server Error"),

    ;
}
package com.nfu.gateway.exception.response

import com.nfu.gateway.exception.constant.ApiError

class ApiErrorResponse(
    val resultCode: String,
    val message: String
) {

    constructor(apiError: ApiError): this(apiError.resultCode, apiError.message)
}
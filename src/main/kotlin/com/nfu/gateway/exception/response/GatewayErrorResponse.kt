package com.nfu.gateway.exception.response

import com.nfu.gateway.exception.constant.GatewayError

class GatewayErrorResponse(
    val resultCode: String,
    val message: String
) {

    constructor(gatewayError: GatewayError): this(gatewayError.resultCode, gatewayError.message)
}
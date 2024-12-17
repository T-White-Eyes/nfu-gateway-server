package com.nfu.gateway.exception

import com.nfu.gateway.exception.constant.GatewayError

class GatewayException(val gatewayError: GatewayError): RuntimeException() {
}
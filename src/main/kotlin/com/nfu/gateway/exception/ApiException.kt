package com.nfu.gateway.exception

import com.nfu.gateway.exception.constant.ApiError

class ApiException(val apiError: ApiError): RuntimeException() {
}
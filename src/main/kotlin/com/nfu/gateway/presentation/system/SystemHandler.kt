package com.nfu.gateway.presentation.system

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class SystemHandler {

    @Value("\${server.port}")
    lateinit var port: String

    @Value("\${spring.application.name}")
    lateinit var applicationName: String

    fun checkHealth(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
            .bodyValue("$applicationName running on $port")
    }
}
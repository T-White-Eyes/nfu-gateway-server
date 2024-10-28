package com.nfu.gateway.presentation.system

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class SystemRouter {

    @Bean
    fun route(systemHandler: SystemHandler): RouterFunction<ServerResponse> {
        return RouterFunctions.route()
            .GET("/api/v1/health-check", systemHandler::checkHealth)
            .build()
    }
}
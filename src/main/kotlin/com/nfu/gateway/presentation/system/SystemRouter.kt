package com.nfu.gateway.presentation.system

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class SystemRouter {

    @Bean
    fun routeSystem(systemHandler: SystemHandler): RouterFunction<ServerResponse> {
        return RouterFunctions.route()
            .path("/api/v1") { builder ->
                builder.GET("/health-check", systemHandler::checkHealth)
            }
            .build()
    }
}
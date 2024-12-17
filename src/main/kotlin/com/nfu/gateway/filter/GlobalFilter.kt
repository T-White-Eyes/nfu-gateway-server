package com.nfu.gateway.filter

import com.nfu.gateway.constant.header.HeaderName
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component

import reactor.core.publisher.Mono
import java.util.UUID

/**
 * Gateway Global Filter
 *
 * @author baetaehyeon
 * @since v1.0.0
 */
@Component
class GlobalFilter: AbstractGatewayFilterFactory<GlobalFilter.Config>(Config::class.java) {

    private val logger = KotlinLogging.logger {}

    data class Config(
        val baseMessage: String,
        val isPreLogger: Boolean,
        val isPostLogger: Boolean,
    )

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val response = exchange.response
            val requestId = UUID.randomUUID().toString()

            if (config.isPreLogger) {
                logger.info { "[$requestId] [${config.baseMessage}] Start" }
                logger.info { "[$requestId] [${config.baseMessage}] Request Information: [${request.method} | ${request.uri}]" }
            }

            val modifiedExchange = addNfuRequestIdHeader(request, requestId)
                .let { modifiedRequest ->
                    exchange
                        .mutate()
                        .request(modifiedRequest)
                        .build()
                }

            return@GatewayFilter chain.filter(modifiedExchange)
                .then(Mono.fromRunnable {
                    if (config.isPostLogger) {
                        logger.info { "[$requestId] [${config.baseMessage}] End, Response Status Code: ${response.statusCode}" }
                    }
                })
        }
    }

    private fun addNfuRequestIdHeader(request: ServerHttpRequest, nfuRequestId: String): ServerHttpRequest {
        return request
            .mutate()
            .header(HeaderName.NFU_REQUEST_ID, nfuRequestId)
            .build()
    }
}
package com.nfu.gateway.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component

import reactor.core.publisher.Mono

/**
 * Gateway Global Filter
 *
 * @author baetaehyeon
 * @since v1.0.0
 */
@Component
class GlobalGatewayFilterFactory: AbstractGatewayFilterFactory<GlobalGatewayFilterFactory.Config>(Config::class.java) {

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

            if (config.isPreLogger) {
                logger.info { "[${config.baseMessage}] Start" }
                logger.info { "[${config.baseMessage}] Request Information: [${request.method} | ${request.uri}]" }
            }

            return@GatewayFilter chain.filter(exchange)
                .then(Mono.fromRunnable {
                    if (config.isPostLogger) {
                        logger.info { "[${config.baseMessage}] End, Response Status Code: ${response.statusCode}" }
                    }
                })
        }
    }
}
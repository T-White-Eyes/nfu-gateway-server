package com.nfu.gateway.filter.ratelimit

import com.nfu.gateway.exception.GatewayException
import com.nfu.gateway.exception.constant.GatewayError
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter
import org.springframework.cloud.gateway.support.HasRouteId
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RequestRateLimiter(
    private val rateLimiter: RateLimiter<RedisRateLimiter.Config>,
): AbstractGatewayFilterFactory<RequestRateLimiter.Config>(Config::class.java) {

    class Config(
        val keyResolver: KeyResolver,
    ): HasRouteId {
        private var routeId: String? = null

        override fun getRouteId(): String = this.routeId!!

        override fun setRouteId(routeId: String) {
            this.routeId = routeId
        }
    }

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val keyResolver = config.keyResolver
            val routeId = config.routeId

            return@GatewayFilter keyResolver.resolve(exchange)
                .flatMap { key -> this.rateLimiter.isAllowed(routeId, key) }
                .flatMap { rateLimitResponse ->
                    when (rateLimitResponse.isAllowed) {
                        true -> chain.filter(exchange)
                        false -> return@flatMap Mono.error(GatewayException(GatewayError.TOO_MANY_REQUESTS))
                    }
                }
        }
    }
}
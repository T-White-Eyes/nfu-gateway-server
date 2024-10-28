package com.nfu.gateway.filter.ratelimit.resolver

import com.nfu.gateway.constant.header.HeaderName
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component(value = "memberIdAsKeyResolver")
class MemberIdAsKeyResolver: KeyResolver {

    private val logger = KotlinLogging.logger {}

    override fun resolve(exchange: ServerWebExchange): Mono<String> {
        val memberId = exchange.request.headers.getFirst(HeaderName.NFU_MEMBER_ID)!!
        logger.info { "### [MemberIdAsKeyResolver] memberId: $memberId ###" }
        return Mono.just(memberId)
    }
}
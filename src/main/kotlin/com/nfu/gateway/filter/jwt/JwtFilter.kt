package com.nfu.gateway.filter.jwt

import com.nfu.gateway.constant.header.HeaderName
import com.nfu.gateway.constant.jwt.JwtPrefix
import com.nfu.gateway.exception.ApiException
import com.nfu.gateway.exception.constant.ApiError
import com.nfu.gateway.util.jwt.JwtPayloadParser
import com.nfu.gateway.util.jwt.JwtVerifier
import com.nfu.gateway.util.startsWithNot
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component

/**
 * Gateway JWT Filter
 *
 * @author baetaehyeon
 * @since v1.0.0
 */
@Component
class JwtFilter(
    private val jwtVerifier: JwtVerifier,
    private val jwtPayloadParser: JwtPayloadParser,
): AbstractGatewayFilterFactory<JwtFilter.Config>(Config::class.java) {

    private val logger = KotlinLogging.logger {}

    class Config

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val requestId = extractRequestId(request)

            logger.debug { "[$requestId] [JwtFilter] Start" }

            val authorizationHeader = extractAuthorizationHeader(request)
            val token = extractToken(authorizationHeader)
            verifyToken(token)

            val payload = jwtPayloadParser.parse(token)

            val modifiedExchange = addNfuMemberIdHeader(request, memberId = payload.id)
                .let { modifiedRequest ->
                    exchange
                        .mutate()
                        .request(modifiedRequest)
                        .build()
                }

            logger.debug { "[$requestId] [JwtFilter] token: $token" }
            logger.debug { "[$requestId] [JwtFilter] memberId: ${payload.id}" }
            logger.debug { "[$requestId] [JwtFilter] memberEmail: ${payload.email}" }
            logger.debug { "[$requestId] [JwtFilter] End" }

            return@GatewayFilter chain.filter(modifiedExchange)
        }
    }

    private fun extractRequestId(request: ServerHttpRequest) =
        request.headers[HeaderName.NFU_REQUEST_ID]?.firstOrNull()!!

    private fun verifyToken(token: String) {
        if (jwtVerifier.isUnverified(token)) {
            throw ApiException(ApiError.UNAUTHORIZED)
        }
    }

    private fun extractToken(authorizationHeader: String): String {
        if (authorizationHeader.isBlank() || authorizationHeader.startsWithNot(JwtPrefix.BEARER)) {
            throw ApiException(ApiError.UNAUTHORIZED)
        }

        return authorizationHeader.substring(JwtPrefix.BEARER.length)
    }

    private fun extractAuthorizationHeader(request: ServerHttpRequest): String {
        return request.headers[HttpHeaders.AUTHORIZATION]?.firstOrNull()
            ?: throw ApiException(ApiError.UNAUTHORIZED)
    }

    /**
     * gateway에서 다른 서버로 요청 시 header 추가
     */
    private fun addNfuMemberIdHeader(request: ServerHttpRequest, memberId: Long): ServerHttpRequest {
        return request
            .mutate()
            .header(HeaderName.NFU_MEMBER_ID, memberId.toString())
//            .header(HeaderName.NFU_PASS_PORT, payload.id.toString()) // TODO 추후 PassPort 형태로 적용
            .build()
    }
}
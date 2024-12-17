package com.nfu.gateway.util.jwt

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtVerifier(
    private val jwtPayloadParser: JwtPayloadParser
) {

    private val logger = KotlinLogging.logger {}

    fun isVerified(token: String): Boolean {
        return try {
            val now = Date()
            val payload = jwtPayloadParser.parse(token)

            if (payload.expiration < now.time) {
                false
            }

            true
        } catch (e: Exception) {
            logger.error { e.toString() }
            false
        }
    }

    fun isUnverified(token: String): Boolean {
        return !isVerified(token)
    }
}
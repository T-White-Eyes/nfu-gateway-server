package com.nfu.gateway.util.jwt

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtValidator(
    private val jwtPayloadParser: JwtPayloadParser
) {

    private val logger = KotlinLogging.logger {}

    fun isValid(token: String): Boolean {
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

    fun isInvalid(token: String): Boolean {
        return !isValid(token)
    }
}
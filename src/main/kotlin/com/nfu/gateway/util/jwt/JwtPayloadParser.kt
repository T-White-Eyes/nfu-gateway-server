package com.nfu.gateway.util.jwt

import com.nfu.gateway.constant.jwt.JwtClaimField
import com.nfu.gateway.constant.jwt.JwtProperties
import com.nfu.gateway.vo.jwt.Payload
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component

@Component
class JwtPayloadParser(
    private val jwtProperties: JwtProperties
) {

    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())

    fun parse(token: String): Payload {
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload

        return Payload(
            id = claims[JwtClaimField.ID].toString().toLong(),
            email = claims[JwtClaimField.EMAIL].toString(),
            issuedAt = claims[JwtClaimField.ISSUED_AT].toString().toLong(),
            expiration = claims[JwtClaimField.EXPIRATION].toString().toLong(),
        )
    }
}
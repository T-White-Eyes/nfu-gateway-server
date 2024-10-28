package com.nfu.gateway.constant.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties (
    val secretKey: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long,
)
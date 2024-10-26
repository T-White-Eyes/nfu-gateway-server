package com.nfu.gateway.vo.jwt

class Payload(
    val id: Long,
    val email: String,
    val issuedAt: Long,
    val expiration: Long
) {
}
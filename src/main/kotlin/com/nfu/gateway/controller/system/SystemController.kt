package com.nfu.gateway.controller.system

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1"])
class SystemController {

    @Value("\${server.port}")
    lateinit var port: String

    @Value("\${spring.application.name}")
    lateinit var applicationName: String

    @RequestMapping(method = [RequestMethod.GET], value = ["/health-check"])
    fun checkHealth(): String {
        return "$applicationName running on $port"
    }
}
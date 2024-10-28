package com.nfu.gateway.controller.eureka.actuator

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["/actuator"])
class EurekaActuatorController {

    @Value("\${server.port}")
    lateinit var port: String

    @Value("\${spring.application.name}")
    lateinit var applicationName: String

    @RequestMapping(method = [RequestMethod.GET], value = ["/info"])
    fun getInfo(): Mono<String> {
        return Mono.just("$applicationName running on $port")
    }
}
package com.nixsolutions.authorizationserver

import com.nixsolutions.financial.config.EnableSimpleDiscovery
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableSimpleDiscovery
class AuthorizationServerApplication

fun main(args: Array<String>) {
    runApplication<AuthorizationServerApplication>(*args)
}

package com.nixsolutions.authorizationserver

import com.nixsolutions.financial.discovery.config.EnableSimpleDiscovery
import com.nixsolutions.financial.security.config.EnableJwtProtection
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableSimpleDiscovery
@EnableJwtProtection(useDefault = false)
class AuthorizationServerApplication

fun main(args: Array<String>) {
    runApplication<AuthorizationServerApplication>(*args)
}

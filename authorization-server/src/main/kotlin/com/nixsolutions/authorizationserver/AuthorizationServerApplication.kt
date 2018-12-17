package com.nixsolutions.authorizationserver

import com.nixsolutions.financial.config.EnableSimpleDiscovery
import com.nixsolutions.financial.security.SecurityProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableSimpleDiscovery
@EnableConfigurationProperties(SecurityProperties::class)
class AuthorizationServerApplication

fun main(args: Array<String>) {
    runApplication<AuthorizationServerApplication>(*args)
}

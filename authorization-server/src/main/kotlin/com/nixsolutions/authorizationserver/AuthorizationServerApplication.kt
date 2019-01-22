package com.nixsolutions.authorizationserver

import com.nixsolutions.authorizationserver.config.AuthorizationServerConfiguration
import com.nixsolutions.financial.discovery.config.EnableSimpleDiscovery
import com.nixsolutions.financial.metrics.CommonMetricsConfig
import com.nixsolutions.financial.security.config.EnableJwtProtection
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@EnableSimpleDiscovery
@EnableJwtProtection(useDefault = false)
@Import(*[CommonMetricsConfig::class, AuthorizationServerConfiguration::class])
class AuthorizationServerApplication

fun main(args: Array<String>) {
    runApplication<AuthorizationServerApplication>(*args)
}

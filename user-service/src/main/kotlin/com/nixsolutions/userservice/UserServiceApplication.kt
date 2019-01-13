package com.nixsolutions.userservice

import com.nixsolutions.financial.security.config.EnableJwtProtection
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.reactive.config.EnableWebFlux

@EnableJpaRepositories
@SpringBootApplication
@EnableWebFlux
@EnableJwtProtection(useDefault = true)
class UserServiceApplication {

}

fun main(args: Array<String>) {
  runApplication<UserServiceApplication>(*args)
}



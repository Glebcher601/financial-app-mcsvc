package com.nixsolutions.userservice

import com.nixsolutions.financial.security.config.EnableJwtProtection
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.reactive.config.EnableWebFlux

@EnableJpaRepositories
@SpringBootApplication
@EnableWebFlux
@EnableJwtProtection
class UserServiceApplication

fun main(args: Array<String>) {
  runApplication<UserServiceApplication>(*args)
}

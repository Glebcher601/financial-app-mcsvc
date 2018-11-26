package com.nixsolutions.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class UserServiceApplication

fun main(args: Array<String>) {
  runApplication<UserServiceApplication>(*args)
}

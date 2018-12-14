package com.nixsolutions.userservice.config

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class WebSecurityConfig: WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity?) {

  }
}

@Component
class JwtAuthenticationConverter : java.util.function.Function<ServerWebExchange, Mono<Authentication>> {


  override fun apply(t: ServerWebExchange): Mono<Authentication> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}
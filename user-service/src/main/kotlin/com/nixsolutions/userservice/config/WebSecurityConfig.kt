package com.nixsolutions.userservice.config

import com.nixsolutions.financial.security.config.HttpSecurityConfigurationHolder
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class WebSecurityConfig {

  @Bean
  fun springSecurityFilterChain(httpSecurityHolder: HttpSecurityConfigurationHolder): SecurityWebFilterChain {
    return httpSecurityHolder.httpSecurity.csrf().disable()
        .authorizeExchange()
        .pathMatchers("/api/**").hasAuthority("admin_permission")
        .and()
        .httpBasic().disable()
        .build()
  }
}


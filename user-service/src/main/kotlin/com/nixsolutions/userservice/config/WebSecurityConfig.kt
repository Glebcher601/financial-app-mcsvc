package com.nixsolutions.userservice.config

import com.nixsolutions.financial.security.config.HttpSecurityConfigurationHolder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class WebSecurityConfig {

  @Bean
  fun springSecurityFilterChain(httpSecurityHolder: HttpSecurityConfigurationHolder): SecurityWebFilterChain {
    return httpSecurityHolder.httpSecurity
        .authorizeExchange()
        .pathMatchers("/protected").authenticated()
        .and()
        .authorizeExchange()
        .pathMatchers("/api/**").authenticated()
        .anyExchange().authenticated()
        .and()
        .build()
  }
}


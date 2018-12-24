package com.nixsolutions.userservice.config

import com.nixsolutions.financial.security.properties.SystemJwtAuthenticationHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class WebSecurityConfig {

  @Autowired
  lateinit var systemJwtAuthenticationHolder: SystemJwtAuthenticationHolder;

  @Bean
  fun springSecurityFilterChain(@Qualifier("preConfiguredHttpSecurity") http: ServerHttpSecurity): SecurityWebFilterChain {
    return http.authorizeExchange()
        .pathMatchers("/protected").authenticated()
        .and()
        .authorizeExchange()
        .pathMatchers("/api/**").authenticated()
        .anyExchange().authenticated()
        .and()
        .build()
  }
}


package com.nixsolutions.financial.security.config

import com.nixsolutions.financial.security.SecurityProperties
import com.nixsolutions.financial.security.authentication.JwtAuthenticationConverter
import com.nixsolutions.financial.security.exception.JwtAuthenticationFailureHandler
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono

@Configuration
@ComponentScan("com.nixsolutions.financial.security")
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@EnableConfigurationProperties(SecurityProperties::class)
class JwtAuthorizationConfiguration {

  @Bean
  fun preConfiguredHttpSecurity(httpSecurity: ServerHttpSecurity,
                                jwtAuthenticationConverter: JwtAuthenticationConverter): ServerHttpSecurity {
    val bearerAuthenticationFilter = AuthenticationWebFilter(ReactiveAuthenticationManager { auth -> Mono.just(auth) })
    bearerAuthenticationFilter.setServerAuthenticationConverter(jwtAuthenticationConverter)
    bearerAuthenticationFilter.setAuthenticationFailureHandler(JwtAuthenticationFailureHandler)

    return httpSecurity.authorizeExchange()
        .and()
        .addFilterAt(bearerAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
    /*.exceptionHandling().accessDeniedHandler(JwtAccessDeniedHandler)
    .and()*/
  }
}


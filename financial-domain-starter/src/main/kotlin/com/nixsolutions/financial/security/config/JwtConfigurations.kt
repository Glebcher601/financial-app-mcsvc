package com.nixsolutions.financial.security.config

import com.nixsolutions.financial.security.authentication.JwtAuthenticationConverter
import com.nixsolutions.financial.security.exception.CustomAuthenticationEntryPoint
import com.nixsolutions.financial.security.exception.CustomAuthenticationFailureHandler
import com.nixsolutions.financial.security.exception.SecurityExceptionHandler
import com.nixsolutions.financial.security.properties.SecurityProperties
import com.nixsolutions.financial.security.properties.SystemJwtAuthenticationHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono

@Configuration
@ComponentScan(
    "com.nixsolutions.financial.security.properties",
    "com.nixsolutions.financial.security.exception")
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@EnableConfigurationProperties(SecurityProperties::class, SystemJwtAuthenticationHolder::class)
class CommonJwtSecurityConfiguration

@ComponentScan(
    "com.nixsolutions.financial.security.verifier",
    "com.nixsolutions.financial.security.authentication")
@Configuration
@Import(CommonJwtSecurityConfiguration::class)
class DefaultJwtAuthorizationConfiguration {

  @Autowired
  lateinit var jwtAuthenticationConverter: JwtAuthenticationConverter
  @Autowired
  lateinit var jwtReactiveAuthenticationManager: ReactiveAuthenticationManager
  @Autowired
  lateinit var customAuthenticationFailureHandler: CustomAuthenticationFailureHandler
  @Autowired
  lateinit var securityExceptionHandler: SecurityExceptionHandler
  @Autowired
  lateinit var customAuthenticationEntryPoint: CustomAuthenticationEntryPoint

  @Bean
  fun preConfiguredHttpSecurity(httpSecurity: ServerHttpSecurity): HttpSecurityConfigurationHolder {
    val bearerAuthenticationFilter = AuthenticationWebFilter(jwtReactiveAuthenticationManager)
    bearerAuthenticationFilter.setServerAuthenticationConverter(jwtAuthenticationConverter)
    bearerAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler)

    return HttpSecurityConfigurationHolder(
        httpSecurity
            .exceptionHandling()
            .accessDeniedHandler(securityExceptionHandler)
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            .and().authorizeExchange()
            .matchers(EndpointRequest.to("health")).permitAll()
            .matchers(EndpointRequest.toAnyEndpoint()).hasAuthority("actuator_permission")
            .and()
            .authorizeExchange()
            .and()
            .addFilterAt(bearerAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION))
  }
}
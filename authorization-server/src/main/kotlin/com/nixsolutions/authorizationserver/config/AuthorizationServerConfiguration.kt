package com.nixsolutions.authorizationserver.config

import com.nixsolutions.financial.security.authentication.JwtAuthenticationConverter
import com.nixsolutions.financial.security.config.decideToAuthenticate
import com.nixsolutions.financial.security.exception.CustomAuthenticationFailureHandler
import com.nixsolutions.financial.security.exception.JwtAccessDeniedHandler
import com.nixsolutions.financial.security.verifier.JwtVerifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@Configuration
@ComponentScan(basePackages = ["com.nixsolutions.financial.security.verifier",
  "com.nixsolutions.financial.security.authentication"])
class AuthorizationServerConfiguration {

  @Autowired
  lateinit var reactiveUserDetailsService: ReactiveUserDetailsService;

  @Autowired
  lateinit var jwtVerifier: JwtVerifier;

  fun tokenAuthenticationFilter(): AuthenticationWebFilter {
    val bearerAuthenticationFilter = AuthenticationWebFilter(ReactiveAuthenticationManager(::decideToAuthenticate))
    bearerAuthenticationFilter.setServerAuthenticationConverter(JwtAuthenticationConverter(jwtVerifier))
    bearerAuthenticationFilter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler)

    return bearerAuthenticationFilter;
  }

  fun basicAuthenticationFilter(): AuthenticationWebFilter {
    val reactiveAuthManager = UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService)
    reactiveAuthManager.setPasswordEncoder(NoOpPasswordEncoder.getInstance())
    val basicAuthWebFilter = AuthenticationWebFilter(reactiveAuthManager)
    basicAuthWebFilter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler)

    return basicAuthWebFilter
  }

  //TODO special security flow for actuator
  @Bean
  fun authorizationServerFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain =
      httpSecurity
          .csrf().disable()
          .addFilterAt(tokenAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
          .authorizeExchange()
          .matchers(EndpointRequest.toAnyEndpoint()).hasAuthority("actuator_permission")
          .pathMatchers("/api/**").authenticated()
          .and()
          .addFilterAt(basicAuthenticationFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
          .exceptionHandling().accessDeniedHandler(JwtAccessDeniedHandler)
          .and()
          .build()
}
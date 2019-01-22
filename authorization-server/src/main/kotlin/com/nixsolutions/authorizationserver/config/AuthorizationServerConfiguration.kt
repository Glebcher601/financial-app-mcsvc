package com.nixsolutions.authorizationserver.config

import com.nixsolutions.financial.security.authentication.JwtAuthenticationConverter
import com.nixsolutions.financial.security.exception.CustomAuthenticationEntryPoint
import com.nixsolutions.financial.security.exception.CustomAuthenticationFailureHandler
import com.nixsolutions.financial.security.exception.SecurityExceptionHandler
import com.nixsolutions.financial.security.verifier.JwtVerifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers

@Configuration
@ComponentScan(basePackages = ["com.nixsolutions.financial.security.verifier",
  "com.nixsolutions.financial.security.authentication"])
class AuthorizationServerConfiguration {

  val actuatorMatcher = EndpointRequest.toAnyEndpoint()
  val tokenCheckMatcher = ServerWebExchangeMatchers.pathMatchers("/api/**/checkToken")
  val tokenObtainMatcher = ServerWebExchangeMatchers.pathMatchers("/api/**/obtainToken")
  val healthEndpointMatcher = EndpointRequest.to("health")

  @Autowired
  lateinit var reactiveUserDetailsService: ReactiveUserDetailsService
  @Autowired
  lateinit var jwtVerifier: JwtVerifier
  @Autowired
  lateinit var customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
  @Autowired
  lateinit var customAuthenticationFailureHandler: CustomAuthenticationFailureHandler;
  @Autowired
  lateinit var securityExceptionHandler: SecurityExceptionHandler
  @Autowired
  lateinit var jwtReactiveAuthenticationManager: ReactiveAuthenticationManager

  fun tokenAuthenticationFilter(): AuthenticationWebFilter {
    val bearerAuthenticationFilter = AuthenticationWebFilter(jwtReactiveAuthenticationManager)
    bearerAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler)
    bearerAuthenticationFilter.setRequiresAuthenticationMatcher(actuatorMatcher);
    bearerAuthenticationFilter.setServerAuthenticationConverter(JwtAuthenticationConverter(jwtVerifier))

    return bearerAuthenticationFilter;
  }

  fun basicAuthenticationFilter(): AuthenticationWebFilter {
    val reactiveAuthManager = UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService)
    //TODO bad things happen here
    reactiveAuthManager.setPasswordEncoder(NoOpPasswordEncoder.getInstance())
    val basicAuthWebFilter = AuthenticationWebFilter(reactiveAuthManager)
    basicAuthWebFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler)
    basicAuthWebFilter.setRequiresAuthenticationMatcher(tokenObtainMatcher)

    val basicAuthenticationConverter = ServerHttpBasicAuthenticationConverter()
    basicAuthWebFilter.setServerAuthenticationConverter(basicAuthenticationConverter)

    return basicAuthWebFilter
  }

  @Bean
  fun authorizationServerFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
    return httpSecurity
        .csrf().disable()
        .addFilterAt(tokenAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        .addFilterAt(basicAuthenticationFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
        .authorizeExchange()
        /*ACL part*/
        .matchers(healthEndpointMatcher).permitAll()
        .matchers(actuatorMatcher).hasAuthority("actuator_permission")
        .matchers(tokenObtainMatcher).authenticated()
        .matchers(tokenCheckMatcher).permitAll()
        .and()
        .exceptionHandling().accessDeniedHandler(securityExceptionHandler)
        .authenticationEntryPoint(customAuthenticationEntryPoint)
        .and()
        .build()
  }

}
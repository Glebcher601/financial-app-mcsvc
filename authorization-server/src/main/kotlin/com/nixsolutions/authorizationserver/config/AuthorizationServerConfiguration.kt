package com.nixsolutions.authorizationserver.config

import com.nixsolutions.financial.security.exception.CustomAuthenticationFailureHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@Configuration
class AuthorizationServerConfiguration {

  @Autowired
  lateinit var reactiveUserDetailsService: ReactiveUserDetailsService;

  fun basicAuthenticationFilter(): AuthenticationWebFilter {
    val reactiveAuthManager = UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService)
    val basicAuthWebFilter = AuthenticationWebFilter(reactiveAuthManager)
    basicAuthWebFilter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler)

    return basicAuthWebFilter
  }

  @Bean
  fun authorizationServerFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain =
      httpSecurity
          .addFilterAt(basicAuthenticationFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
          .authorizeExchange()
          .pathMatchers("/api/v0/signUp").authenticated()
          .pathMatchers("/api/v0/checkToken").authenticated()
          .and().build()
}
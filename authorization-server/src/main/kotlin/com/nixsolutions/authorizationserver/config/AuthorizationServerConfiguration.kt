package com.nixsolutions.authorizationserver.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@Configuration
class AuthorizationServerConfiguration {

  @Autowired
  lateinit var reactiveUserDetailsService: ReactiveUserDetailsService;

  @Bean
  fun basicAuthenticationFilter() {
    val reactiveAuthManager = UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService)
    val authenticationWebFilter = AuthenticationWebFilter(reactiveAuthManager)
  }

}
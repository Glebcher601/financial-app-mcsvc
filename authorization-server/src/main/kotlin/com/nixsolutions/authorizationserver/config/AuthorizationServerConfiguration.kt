package com.nixsolutions.authorizationserver.config

import com.nixsolutions.financial.security.exception.CustomAuthenticationFailureHandler
import com.nixsolutions.financial.security.exception.JwtAccessDeniedHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@Configuration
@ComponentScan("com.nixsolutions.financial.security.verifier")
class AuthorizationServerConfiguration {

  @Autowired
  lateinit var reactiveUserDetailsService: ReactiveUserDetailsService;

  fun basicAuthenticationFilter(): AuthenticationWebFilter {
    val reactiveAuthManager = UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService)
    reactiveAuthManager.setPasswordEncoder(NoOpPasswordEncoder.getInstance())
    val basicAuthWebFilter = AuthenticationWebFilter(reactiveAuthManager)
    basicAuthWebFilter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler)

    return basicAuthWebFilter
  }

  @Bean
  fun authorizationServerFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain =
      httpSecurity
          .csrf().disable()
          .addFilterAt(basicAuthenticationFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
          .exceptionHandling().accessDeniedHandler(JwtAccessDeniedHandler)
          .and()
          .build()
}
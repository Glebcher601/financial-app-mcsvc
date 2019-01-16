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
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class BFilter(authenticationManager: ReactiveAuthenticationManager?) : AuthenticationWebFilter(authenticationManager) {
  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    return super.filter(exchange, chain)
  }
}

class TFilter(authenticationManager: ReactiveAuthenticationManager?) : AuthenticationWebFilter(authenticationManager) {
  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    return super.filter(exchange, chain)
  }
}

@Configuration
@ComponentScan(basePackages = ["com.nixsolutions.financial.security.verifier",
  "com.nixsolutions.financial.security.authentication"])
class AuthorizationServerConfiguration {

  val actuatorMatcher = EndpointRequest.toAnyEndpoint()

  @Autowired
  lateinit var reactiveUserDetailsService: ReactiveUserDetailsService;

  @Autowired
  lateinit var jwtVerifier: JwtVerifier;

  fun tokenAuthenticationFilter(): AuthenticationWebFilter {
    val bearerAuthenticationFilter = TFilter(ReactiveAuthenticationManager(::decideToAuthenticate))
    bearerAuthenticationFilter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler)
    bearerAuthenticationFilter.setRequiresAuthenticationMatcher(actuatorMatcher);
    bearerAuthenticationFilter.setServerAuthenticationConverter(JwtAuthenticationConverter(jwtVerifier))

    return bearerAuthenticationFilter;
  }

  fun basicAuthenticationFilter(): AuthenticationWebFilter {
    val reactiveAuthManager = UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService)
    //TODO bas things happen here
    reactiveAuthManager.setPasswordEncoder(NoOpPasswordEncoder.getInstance())
    val basicAuthWebFilter = BFilter(reactiveAuthManager)
    basicAuthWebFilter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler)
    basicAuthWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/api/**"))

    val basicAuthenticationConverter = ServerHttpBasicAuthenticationConverter()
    val serverAuthenticationConverter = ServerAuthenticationConverter { exchange ->
      basicAuthenticationConverter.convert(exchange)
          .switchIfEmpty(Mono.error(BadCredentialsException("Wrong authentication type provided")))
    }

    basicAuthWebFilter.setServerAuthenticationConverter(serverAuthenticationConverter)

    return basicAuthWebFilter
  }

  //TODO special security flow for actuator
  @Bean
  fun authorizationServerFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain =
      httpSecurity
          .csrf().disable()
          .authorizeExchange()
          .matchers(EndpointRequest.toAnyEndpoint()).hasAuthority("actuator_permission")
          .pathMatchers("/api/**").authenticated()
          .and()
          .addFilterAt(tokenAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
          .addFilterAt(basicAuthenticationFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
          .exceptionHandling().accessDeniedHandler(JwtAccessDeniedHandler)
          .and()
          .build()
}
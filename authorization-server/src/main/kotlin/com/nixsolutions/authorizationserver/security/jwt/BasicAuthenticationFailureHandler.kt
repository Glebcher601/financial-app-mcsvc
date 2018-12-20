package com.nixsolutions.authorizationserver.security.jwt

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import reactor.core.publisher.Mono

class BasicAuthenticationFailureHandler : ServerAuthenticationFailureHandler {
  override fun onAuthenticationFailure(webFilters: WebFilterExchange, exception: AuthenticationException): Mono<Void> {
    return Mono.empty()
  }
}
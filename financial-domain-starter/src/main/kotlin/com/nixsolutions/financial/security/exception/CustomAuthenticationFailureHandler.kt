package com.nixsolutions.financial.security.exception

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import reactor.core.publisher.Mono

object CustomAuthenticationFailureHandler : ServerAuthenticationFailureHandler {
  override fun onAuthenticationFailure(webFilters: WebFilterExchange,
                                       exception: AuthenticationException): Mono<Void> {
    webFilters.exchange.response.statusCode = HttpStatus.FORBIDDEN
    webFilters.exchange.response.headers.contentType = MediaType.APPLICATION_JSON

    return Mono.just(
        getCustomErrorAttributes(exception.message ?: "Authentication Exception", webFilters.exchange.request))
        .flatMap { writeJsonErrorResponse(webFilters.exchange, it) }
  }
}
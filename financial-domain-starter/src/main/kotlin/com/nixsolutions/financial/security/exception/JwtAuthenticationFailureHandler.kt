package com.nixsolutions.financial.security.exception

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import reactor.core.publisher.Mono

object JwtAuthenticationFailureHandler : ServerAuthenticationFailureHandler {
  override fun onAuthenticationFailure(webFilters: WebFilterExchange,
                                       exception: AuthenticationException): Mono<Void> {
    val response = webFilters.exchange.response
    response.statusCode = HttpStatus.UNAUTHORIZED
    val dataBuffer = response.bufferFactory().wrap((exception.message ?: "Security exception").toByteArray())
    return response.writeWith(Mono.just(dataBuffer))
  }
}
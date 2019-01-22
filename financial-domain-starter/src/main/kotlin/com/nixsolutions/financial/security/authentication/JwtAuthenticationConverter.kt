package com.nixsolutions.financial.security.authentication

import com.nixsolutions.financial.security.SecurityConstants
import com.nixsolutions.financial.security.verifier.JwtVerifier
import io.jsonwebtoken.Claims
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.core.publisher.onErrorResume

@Component
class JwtAuthenticationConverter(val jwtVerifier: JwtVerifier) : ServerAuthenticationConverter {

  override fun convert(exchange: ServerWebExchange): Mono<Authentication> =
      Mono.justOrEmpty(exchange)
          .flatMap { getJwtFromRequest(it.request) }
          .map { UsernamePasswordAuthenticationToken(it, null) }
}

fun getJwtFromRequest(request: ServerHttpRequest): Mono<String> {
  val bearerToken = request.headers[SecurityConstants.AUTHORIZATION]?.first()

  if (bearerToken.isNullOrEmpty()) {
    return Mono.empty()
  }

  return extractTokenValue(bearerToken.orEmpty())
}

fun extractTokenValue(bearerToken: String): Mono<String> {
  if (!bearerToken.startsWith("Bearer"))
    return Mono.empty<String>()
  return Mono.just(bearerToken.substring(7, bearerToken.length))
}

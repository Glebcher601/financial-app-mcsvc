package com.nixsolutions.financial.security.authentication

import com.nixsolutions.financial.security.SecurityConstants
import com.nixsolutions.financial.security.verifier.JwtVerifier
import io.jsonwebtoken.Claims
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationConverter(val jwtVerifier: JwtVerifier) : ServerAuthenticationConverter {

  override fun convert(exchange: ServerWebExchange): Mono<Authentication> =
      Mono.justOrEmpty(exchange)
          .map { getJwtFromRequest(it.request) }
          .map { jwtVerifier.parseToken(it) }
          .onErrorResume({ _ -> Mono.empty() })
          /*.onErrorMap(
              { ex -> ex is AuthenticationException},
              { ex -> ResponseStatusException(HttpStatus.FORBIDDEN, ex.message, ex)})*/
          .flatMap { toAuthenticationMono(it) }
          .log()
}

fun getJwtFromRequest(request: ServerHttpRequest): String? {
  val bearerToken = request.headers[SecurityConstants.AUTHORIZATION]?.first { it.startsWith(SecurityConstants.BEARER_TYPE) }
  return if (bearerToken.isNullOrBlank()) "" else bearerToken?.substring(7, bearerToken.length)
}

fun toAuthenticationMono(claims: Claims): Mono<Authentication> {
  val permissions = claims.get(SecurityConstants.PERMISSIONS, ArrayList::class.java)
      .map { SimpleGrantedAuthority(it.toString()) }

  return Mono.justOrEmpty(claims.subject)
      .map { UsernamePasswordAuthenticationToken(it, null, permissions) }
}
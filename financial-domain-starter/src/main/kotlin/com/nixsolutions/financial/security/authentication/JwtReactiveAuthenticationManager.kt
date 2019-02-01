package com.nixsolutions.financial.security.authentication

import com.nixsolutions.financial.security.SecurityConstants
import com.nixsolutions.financial.security.verifier.JwtVerifier
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtReactiveAuthenticationManager(val jwtVerifier: JwtVerifier): ReactiveAuthenticationManager {

  override fun authenticate(authentication: Authentication): Mono<Authentication> {
    return Mono.just(authentication)
        .map { it.principal as String }
        .map { jwtVerifier.parseToken(it) }
        .flatMap(::toAuthenticationMono)
        .log()
  }

  private fun toAuthenticationMono(claims: Claims): Mono<out Authentication> {
    val permissions = claims.get(SecurityConstants.PERMISSIONS, ArrayList::class.java)
        .map { SimpleGrantedAuthority(it.toString()) }

    return Mono.justOrEmpty(claims.subject)
        .map { UsernamePasswordAuthenticationToken(it, null, permissions) }
  }
}
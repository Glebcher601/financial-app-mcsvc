package com.nixsolutions.userservice.config

import com.nixsolutions.financial.security.SecurityConstants.AUTHORIZATION
import com.nixsolutions.financial.security.SecurityConstants.BEARER_TYPE
import com.nixsolutions.financial.security.SecurityConstants.PERMISSIONS
import com.nixsolutions.financial.security.verifier.JwtVerifier
import io.jsonwebtoken.Claims
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
class WebSecurityConfig {

  @Autowired
  lateinit var jwtAuthenticationConverter: JwtAuthenticationConverter

  @Bean
  fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {

    val bearerAuthenticationFilter = AuthenticationWebFilter(ReactiveAuthenticationManager { auth -> Mono.just(auth) })
    bearerAuthenticationFilter.setServerAuthenticationConverter(jwtAuthenticationConverter)

    return http.authorizeExchange()
        .and()
        .authorizeExchange()
        .pathMatchers("/protected").authenticated()
        .and()
        .addFilterAt(bearerAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .authorizeExchange()
        .anyExchange().permitAll()
        .and()
        .build()
  }
}


@Component
class JwtAuthenticationConverter : ServerAuthenticationConverter {

  private val log = LoggerFactory.getLogger(this::class.java)

  @Autowired
  lateinit var jwtVerifier: JwtVerifier

  override fun convert(exchange: ServerWebExchange): Mono<Authentication> =
      Mono.justOrEmpty(exchange)
          .map { getJwtFromRequest(it.request) }
          .map { jwtVerifier.parseToken(it) }
          .flatMap { toAuthenticationMono(it) }
          .log()
}

fun getJwtFromRequest(request: ServerHttpRequest): String? {
  val bearerToken = request.headers[AUTHORIZATION]?.first { it.startsWith(BEARER_TYPE) }
  return if (bearerToken.isNullOrBlank()) null else bearerToken?.substring(7, bearerToken.length)
}

fun toAuthenticationMono(claims: Claims): Mono<Authentication> {
  val permissions = claims.get(PERMISSIONS, ArrayList::class.java)
      .map { SimpleGrantedAuthority(it.toString()) }

  return Mono.justOrEmpty(claims.subject)
      .map { UsernamePasswordAuthenticationToken(it, null, permissions) }
}


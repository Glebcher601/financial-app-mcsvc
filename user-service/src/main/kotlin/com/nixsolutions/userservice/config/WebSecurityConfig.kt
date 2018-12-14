package com.nixsolutions.userservice.config

import com.nixsolutions.financial.security.JwtVerifier
import com.nixsolutions.financial.security.SecurityConstants.AUTHORIZATION
import com.nixsolutions.financial.security.SecurityConstants.BEARER_TYPE
import com.nixsolutions.financial.security.SecurityConstants.NAME
import com.nixsolutions.financial.security.exception.InvalidTokenException
import com.nixsolutions.userservice.service.ReactiveUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

  @Autowired
  lateinit var jwtAuthenticationConverter: JwtAuthenticationConverter;

  @Bean
  fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    return http.build();
  }



}

@Component
class JwtAuthenticationConverter : ServerAuthenticationConverter {

  private val log = LoggerFactory.getLogger(this::class.java)

  @Autowired
  lateinit var jwtVerifier: JwtVerifier

  @Autowired
  lateinit var userService: ReactiveUserService

  override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
    val jwtFromRequest = getJwtFromRequest(exchange.request)
    try {
      val token = jwtVerifier.parseToken(jwtFromRequest)

      return userService.getByLogin(token.get(NAME, String::class.java))
          .map {
            UsernamePasswordAuthenticationToken(it,
                null, it.permissions.map { SimpleGrantedAuthority(it.key) })
          }

    } catch (e: InvalidTokenException) {
      exchange.response.statusCode = UNAUTHORIZED
    }
    return Mono.empty()
  }

  private fun getJwtFromRequest(request: ServerHttpRequest): String? {
    val bearerToken = request.headers[AUTHORIZATION]?.first { it.startsWith(BEARER_TYPE) }
    return if (bearerToken.isNullOrBlank()) null else bearerToken?.substring(7, bearerToken.length)
  }


}
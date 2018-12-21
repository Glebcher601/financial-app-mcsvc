package com.nixsolutions.financial.security.exception

import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

object JwtAccessDeniedHandler : ServerAccessDeniedHandler {
  override fun handle(webExchange: ServerWebExchange,
                      exception: AccessDeniedException): Mono<Void> {
    val response = webExchange.response
    response.statusCode = HttpStatus.UNAUTHORIZED
    val dataBuffer = response.bufferFactory().wrap((exception.message ?: "Security exception").toByteArray())
    return response.writeWith(Mono.just(dataBuffer))
  }
}
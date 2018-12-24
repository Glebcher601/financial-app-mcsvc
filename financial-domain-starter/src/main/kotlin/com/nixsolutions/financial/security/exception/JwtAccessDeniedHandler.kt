package com.nixsolutions.financial.security.exception

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

object JwtAccessDeniedHandler : ServerAccessDeniedHandler {
  override fun handle(webExchange: ServerWebExchange,
                      exception: AccessDeniedException): Mono<Void> {
    webExchange.response.statusCode = HttpStatus.UNAUTHORIZED
    webExchange.response.headers.contentType = MediaType.APPLICATION_JSON

    return ReactiveSecurityContextHolder.getContext()
        .map { it.authentication }
        .map { it.authorities }
        .map { it.toString() }
        .map { "Denied with permissions: $it" }
        .map { getCustomErrorAttributes(it, webExchange.request) }
        .flatMap { writeJsonErrorResponse(webExchange, it) }
  }
}
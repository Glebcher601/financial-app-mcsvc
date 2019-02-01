package com.nixsolutions.financial.security.exception

import com.nixsolutions.financial.metrics.MeterRegistryAware
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomAuthenticationEntryPoint(override var meterRegistry: MeterRegistry) : ServerAuthenticationEntryPoint, MeterRegistryAware {
  override fun commence(exchange: ServerWebExchange, exception: AuthenticationException): Mono<Void> {
    meterRegistry.counter("authenticationFailed").increment()

    exchange.response.statusCode = HttpStatus.FORBIDDEN
    exchange.response.headers.contentType = MediaType.APPLICATION_JSON

    return Mono.just(
        getCustomErrorAttributes(
            exception.message ?: "Authentication Exception",
            exchange.request,
            HttpStatus.FORBIDDEN))
        .flatMap { writeJsonErrorResponse(exchange, it) }
  }
}
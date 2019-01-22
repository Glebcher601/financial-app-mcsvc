package com.nixsolutions.financial.security.exception

import com.nixsolutions.financial.metrics.MeterRegistryAware
import com.nixsolutions.financial.metrics.requestPathMetrics
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityExceptionHandler(override var meterRegistry: MeterRegistry) : ServerAccessDeniedHandler, MeterRegistryAware {
  override fun handle(webExchange: ServerWebExchange,
                      exception: AccessDeniedException): Mono<Void> {
    webExchange.response.headers.contentType = MediaType.APPLICATION_JSON
    meterRegistry.counter("authorizationFailed", *requestPathMetrics(webExchange))
        .increment()

    return ReactiveSecurityContextHolder.getContext()
        .map { it.authentication }
        .flatMap { determineFailureType(it, webExchange) }
  }

  private fun determineFailureType(auth: Authentication, webExchange: ServerWebExchange): Mono<Void> {
    return if (auth.principal is AuthenticationException)
      onAuthenticationFailure(auth.principal as AuthenticationException, webExchange)
    else
      onAuthorizationFailure(auth, webExchange)
  }

  private fun onAuthorizationFailure(auth: Authentication, webExchange: ServerWebExchange): Mono<Void> {
    webExchange.response.statusCode = HttpStatus.UNAUTHORIZED

    return writeJsonErrorResponse(webExchange,
        getCustomErrorAttributes(
            "Denied with permissions: ${auth.authorities}",
            webExchange.request,
            HttpStatus.UNAUTHORIZED))
  }

  //FIXME dead code
  private fun onAuthenticationFailure(exception: AuthenticationException, webExchange: ServerWebExchange): Mono<Void> {
    webExchange.response.statusCode = HttpStatus.FORBIDDEN

    return writeJsonErrorResponse(webExchange,
        getCustomErrorAttributes(
            exception.message ?: "Authentication failed",
            webExchange.request,
            HttpStatus.FORBIDDEN))
  }

}
package com.nixsolutions.financial.security.exception

import com.nixsolutions.financial.metrics.MeterRegistryAware
import com.nixsolutions.financial.metrics.requestPathMetrics
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomAuthenticationFailureHandler(override var meterRegistry: MeterRegistry) :
    ServerAuthenticationFailureHandler, CustomAuthenticationEntryPoint(meterRegistry), MeterRegistryAware {
  override fun onAuthenticationFailure(webFilters: WebFilterExchange,
                                       exception: AuthenticationException): Mono<Void> {
    meterRegistry.counter("authenticationFailed", *requestPathMetrics(webFilters.exchange)).increment()
    return super.commence(webFilters.exchange, exception)
  }
}
package com.nixsolutions.financial.security.exception

import com.nixsolutions.financial.metrics.MeterRegistryAware
import io.micrometer.core.instrument.MeterRegistry
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
    return super.commence(webFilters.exchange, exception)
  }
}
package com.nixsolutions.userservice.endpoint

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class ContextFilter : WebFilter {

  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    val filter = chain.filter(exchange);
    return filter.subscriberContext { context -> context.put("azazazaz", "aaaaaa") }
  }
}
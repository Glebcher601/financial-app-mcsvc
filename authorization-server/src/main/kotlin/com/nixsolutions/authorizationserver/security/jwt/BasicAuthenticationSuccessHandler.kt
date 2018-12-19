package com.nixsolutions.authorizationserver.security.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import reactor.core.publisher.Mono

//TODO return body
class BasicAuthenticationSuccessHandler : ServerAuthenticationSuccessHandler {


  override fun onAuthenticationSuccess(webFilters: WebFilterExchange,
                                       authentication: Authentication) = Mono.empty<Void>()
}
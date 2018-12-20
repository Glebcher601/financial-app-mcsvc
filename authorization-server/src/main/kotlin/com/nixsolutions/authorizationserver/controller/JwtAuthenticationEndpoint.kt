package com.nixsolutions.authorizationserver.controller

import com.nixsolutions.authorizationserver.security.jwt.JwtTokenProvider
import com.nixsolutions.authorizationserver.security.payload.JwtTokenResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@RestController
class JwtAuthenticationEndpoint {

  @Autowired
  lateinit var tokenProvider: JwtTokenProvider;

  @PostMapping(path = ["/obtainToken"])
  @PreAuthorize("isAuthenticated()")
  fun obtainToken(@AuthenticationPrincipal authentication: Authentication): Mono<ServerResponse> {
    val jwtTokenResponse = JwtTokenResponse(tokenProvider.generateToken(authentication))
    return ServerResponse.ok()
        .header(AUTHORIZATION, "Bearer " + jwtTokenResponse.accessToken)
        .body(BodyInserters.fromObject(jwtTokenResponse))
  }

  @PostMapping(path = ["/checkToken"])
  fun checkToken() = ServerResponse.ok().build()
}
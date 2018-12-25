package com.nixsolutions.authorizationserver.controller

import com.nixsolutions.authorizationserver.security.jwt.JwtTokenProvider
import com.nixsolutions.authorizationserver.security.payload.JwtTokenResponse
import com.nixsolutions.financial.security.verifier.JwtVerifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/api/v0"])
class JwtAuthenticationEndpoint {

  @Autowired
  lateinit var tokenProvider: JwtTokenProvider;

  @Autowired
  lateinit var jwtVerifier: JwtVerifier;

  @PostMapping(path = ["/obtainToken"])
  @PreAuthorize("isAuthenticated()")
  fun obtainToken(@AuthenticationPrincipal authentication: Authentication): Mono<ResponseEntity<JwtTokenResponse>> {
    val jwtTokenResponse = JwtTokenResponse(tokenProvider.generateToken(authentication))
    return Mono.just(ResponseEntity.ok()
        .header(AUTHORIZATION, "Bearer " + jwtTokenResponse.accessToken)
        .body(jwtTokenResponse))
  }

  @PostMapping(path = ["/checkToken"])
  fun checkToken() = ServerResponse.ok().build()

  //TODO signup
  fun signUp() = null
}
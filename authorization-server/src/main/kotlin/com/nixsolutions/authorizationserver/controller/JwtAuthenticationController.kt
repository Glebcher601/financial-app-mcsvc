package com.nixsolutions.authorizationserver.controller

import com.nixsolutions.authorizationserver.security.jwt.JwtTokenProvider
import com.nixsolutions.authorizationserver.security.payload.JwtTokenResponse
import com.nixsolutions.authorizationserver.security.payload.LoginRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.BeanIds
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/api/v0/auth"])
class JwtAuthenticationController {

  @Autowired
  @Qualifier(value = BeanIds.AUTHENTICATION_MANAGER)
  private lateinit var authenticationManager: AuthenticationManager

  @Autowired
  private lateinit var tokenProvider: JwtTokenProvider;

  @PostMapping(path = ["/login"])
  fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<JwtTokenResponse> {
    val authentication = authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken(
            loginRequest.userName,
            loginRequest.password))

    SecurityContextHolder.getContext().authentication = authentication

    val jwtTokenResponse = JwtTokenResponse(tokenProvider.generateToken(authentication))
    return ResponseEntity.ok(jwtTokenResponse)
  }

}

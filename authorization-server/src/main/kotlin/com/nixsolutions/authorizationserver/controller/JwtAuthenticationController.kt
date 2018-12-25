package com.nixsolutions.authorizationserver.controller

import com.nixsolutions.authorizationserver.security.jwt.JwtTokenProvider
import com.nixsolutions.authorizationserver.security.payload.JwtTokenResponse
import com.nixsolutions.authorizationserver.security.payload.LoginRequest
import com.nixsolutions.authorizationserver.security.payload.toUserPasswordToken
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

//@Deprecated("o_O")
//@RestController
//@RequestMapping(path = ["/api/v0"])
@Deprecated("")
class JwtAuthenticationController {

  //@Autowired
  //@Qualifier(value = BeanIds.AUTHENTICATION_MANAGER)
  private lateinit var authenticationManager: AuthenticationManager

  //@Autowired
  private lateinit var tokenProvider: JwtTokenProvider;

  //@PostMapping(path = ["/login"])
  fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<JwtTokenResponse> {
    val authentication = authenticationManager.authenticate(loginRequest.toUserPasswordToken())
    SecurityContextHolder.getContext().authentication = authentication

    val jwtTokenResponse = JwtTokenResponse(tokenProvider.generateToken(authentication))
    return ResponseEntity.ok(jwtTokenResponse)
  }

}

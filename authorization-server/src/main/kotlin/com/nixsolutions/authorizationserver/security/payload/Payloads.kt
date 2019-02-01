package com.nixsolutions.authorizationserver.security.payload

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import javax.validation.constraints.NotBlank

const val BEARER_TOKEN = "Bearer"


typealias UsrPassToken = UsernamePasswordAuthenticationToken

fun LoginRequest.toUserPasswordToken(): UsrPassToken = UsrPassToken(this
    .userName, this.password)

data class LoginRequest(
    @NotBlank
    val userName: String,
    @NotBlank
    val password: String
)

data class JwtTokenResponse(
    val accessToken: String,
    val tokenType: String = BEARER_TOKEN
)
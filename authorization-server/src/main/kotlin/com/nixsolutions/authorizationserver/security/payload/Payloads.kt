package com.nixsolutions.authorizationserver.security.payload

import javax.validation.constraints.NotBlank

const val BEARER_TOKEN = "Bearer"

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
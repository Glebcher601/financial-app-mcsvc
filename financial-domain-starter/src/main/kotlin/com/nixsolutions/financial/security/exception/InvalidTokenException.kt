package com.nixsolutions.financial.security.exception

import org.springframework.security.core.AuthenticationException

class InvalidTokenException : AuthenticationException {

  @JvmOverloads constructor(s: String = "Token has invalid structure or is counterfeit") : super(s)

  constructor(s: String, throwable: Throwable) : super(s, throwable)
}
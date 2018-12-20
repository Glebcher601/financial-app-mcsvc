package com.nixsolutions.financial.security.exception

class InvalidTokenException : RuntimeException {

  @JvmOverloads constructor(s: String = "Token has invalid structure or is counterfeit") : super(s) {}

  constructor(s: String, throwable: Throwable) : super(s, throwable) {}

  constructor(throwable: Throwable) : super(throwable) {}
}

package com.nixsolutions.financial.security.exception

class TokenExpiredException : RuntimeException {

  @JvmOverloads constructor(s: String = "Token expired") : super(s) {}

  constructor(s: String, throwable: Throwable) : super(s, throwable) {}

  constructor(throwable: Throwable) : super(throwable) {}
}

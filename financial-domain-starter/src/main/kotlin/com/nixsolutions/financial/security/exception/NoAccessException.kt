package com.nixsolutions.financial.security.exception

class NoAccessException : RuntimeException {

  @JvmOverloads constructor(s: String = "No acces to the resource") : super(s) {}

  constructor(s: String, throwable: Throwable) : super(s, throwable) {}

  constructor(throwable: Throwable) : super(throwable) {}
}

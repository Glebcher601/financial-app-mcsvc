package com.nixsolutions.financial.security.verifier

import io.jsonwebtoken.Claims
import java.util.*

interface JwtVerifier {
  @Deprecated("")
  fun isExpired(claims: Claims): Boolean {
    return claims.expiration.toInstant().isBefore(Date().toInstant())
  }

  fun parseToken(token: String?): Claims
}

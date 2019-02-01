package com.nixsolutions.financial.security.verifier

import io.jsonwebtoken.Claims

interface JwtVerifier {
  fun parseToken(token: String?): Claims
}

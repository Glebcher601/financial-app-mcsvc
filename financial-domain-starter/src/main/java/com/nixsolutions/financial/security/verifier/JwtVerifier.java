package com.nixsolutions.financial.security.verifier;

import java.util.Date;
import io.jsonwebtoken.Claims;

public interface JwtVerifier
{
  @Deprecated
  default boolean isExpired(Claims claims)
  {
    return claims.getExpiration().toInstant().isBefore(new Date().toInstant());
  }

  Claims parseToken(String token);
}

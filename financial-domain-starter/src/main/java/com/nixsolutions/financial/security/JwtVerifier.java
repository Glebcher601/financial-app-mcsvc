package com.nixsolutions.financial.security;

import java.util.Date;
import io.jsonwebtoken.Claims;

public interface JwtVerifier
{
  AccessDecision hasAccess(Claims claims, String[] rolesNeeded);

  default boolean isExpired(Claims claims)
  {
    return claims.getExpiration().toInstant().isBefore(new Date().toInstant());
  }

  Claims parseToken(String token);
}

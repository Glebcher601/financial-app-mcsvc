package com.nixsolutions.financial.security.verifier;

import com.nixsolutions.financial.security.AccessDecision;
import io.jsonwebtoken.Claims;

import java.util.Date;

public interface JwtVerifier
{
  AccessDecision hasAccess(Claims claims, String[] rolesNeeded);

  @Deprecated
  default boolean isExpired(Claims claims)
  {
    return claims.getExpiration().toInstant().isBefore(new Date().toInstant());
  }

  Claims parseToken(String token);
}

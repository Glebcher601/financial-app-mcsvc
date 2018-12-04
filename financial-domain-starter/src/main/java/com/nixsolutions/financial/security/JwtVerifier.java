package com.nixsolutions.financial.security;

import io.jsonwebtoken.Claims;

public interface JwtVerifier
{
  boolean hasAccess(String token, String roleNeeded);

  Claims parseToken(String token);
}

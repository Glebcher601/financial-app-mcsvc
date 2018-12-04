package com.nixsolutions.financial.security;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
@ConditionalOnProperty(prefix = "financialDomain", name = "security.jwtSecret")
public class SecretAwareJwtVerifier implements JwtVerifier
{
  private String jwtSecret;

  public SecretAwareJwtVerifier(@Value("financialDomain.security.jwtSecret") String jwtSecret)
  {
    this.jwtSecret = jwtSecret;
  }

  @Override
  public boolean hasAccess(String token, String roleNeeded) throws InvalidTokenException
  {
    return false;
  }

  @Override
  public Claims parseToken(String token) throws InvalidTokenException
  {
    if (isEmpty(token))
    {
      throw new InvalidTokenException();
    }

    try
    {
      return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody();
    }
    catch (Exception ex)
    {
      throw new InvalidTokenException();
    }
  }

}

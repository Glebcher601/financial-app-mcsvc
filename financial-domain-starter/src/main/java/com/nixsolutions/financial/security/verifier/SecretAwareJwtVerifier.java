package com.nixsolutions.financial.security.verifier;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.nixsolutions.financial.security.SecurityProperties;
import com.nixsolutions.financial.security.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
@ConditionalOnProperty(prefix = "financial-domain.security", name = "jwtSecret")
public class SecretAwareJwtVerifier implements JwtVerifier
{
  private SecurityProperties securityProperties;

  public SecretAwareJwtVerifier(SecurityProperties securityProperties)
  {
    this.securityProperties = securityProperties;
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
      return Jwts.parser()
          .setSigningKey(securityProperties.getJwtSecret().getBytes())
          .parseClaimsJws(token)
          .getBody();
    }
    catch (Exception ex)
    {
      throw new InvalidTokenException(ex);
    }
  }
}

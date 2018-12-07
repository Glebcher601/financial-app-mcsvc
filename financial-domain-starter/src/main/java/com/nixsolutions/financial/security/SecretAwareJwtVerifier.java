package com.nixsolutions.financial.security;

import static com.nixsolutions.financial.security.SecurityConstants.ROLES;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import java.util.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.nixsolutions.financial.security.exception.InvalidTokenException;
import com.nixsolutions.financial.security.exception.NoAccessException;
import com.nixsolutions.financial.security.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
@ConditionalOnProperty(prefix = "financialDomain", name = "security.jwtSecret")
public class SecretAwareJwtVerifier implements JwtVerifier
{
  private String jwtSecret;

  public SecretAwareJwtVerifier(@Value("${financialDomain.security.jwtSecret}") String jwtSecret)
  {
    this.jwtSecret = jwtSecret;
  }

  @Override
  public AccessDecision hasAccess(Claims claims, String[] rolesNeeded) throws NoAccessException, TokenExpiredException
  {
    if (!ArrayUtils.contains(rolesNeeded, claims.get(ROLES)))
    {
      return AccessDecision.rejectWithError(new NoAccessException("Insufficient user role to access desired resource"));
    }

    return AccessDecision.grantAccess();
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
          .setSigningKey(jwtSecret.getBytes())
          .parseClaimsJws(token)
          .getBody();
    }
    catch (Exception ex)
    {
      throw new InvalidTokenException();
    }
  }
}

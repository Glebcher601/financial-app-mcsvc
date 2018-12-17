package com.nixsolutions.financial.security.verifier;

import com.nixsolutions.financial.security.AccessDecision;
import com.nixsolutions.financial.security.SecurityProperties;
import com.nixsolutions.financial.security.exception.InvalidTokenException;
import com.nixsolutions.financial.security.exception.NoAccessException;
import com.nixsolutions.financial.security.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.nixsolutions.financial.security.SecurityConstants.ROLES;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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

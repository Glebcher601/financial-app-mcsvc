package com.nixsolutions.financial.security;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Deprecated
public class AccessDecision
{
  private AccessDecision()
  {
  }

  private Exception capturedException;

  public boolean canProceed()
  {
    return isNull(capturedException);
  }

  public void throwUnderlyingException() throws Exception
  {
    if (nonNull(capturedException))
    {
      throw capturedException;
    }
  }

  public static AccessDecision rejectWithError(Exception capturedException)
  {
    AccessDecision accessDecision = new AccessDecision();
    accessDecision.capturedException = capturedException;
    return accessDecision;
  }

  public static AccessDecision grantAccess()
  {
    return new AccessDecision();
  }

}

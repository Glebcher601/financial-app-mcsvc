package com.nixsolutions.financial.security.aspect;

import java.lang.reflect.Parameter;

@Deprecated
public interface AuthorizationHeaderMethodParamIdentifier
{
  boolean test(Parameter methodParam);
}

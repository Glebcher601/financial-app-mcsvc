package com.nixsolutions.financial.security.aspect;

import java.lang.reflect.Parameter;

public interface AuthorizationHeaderMethodParamIdentifier
{
  boolean test(Parameter methodParam);
}

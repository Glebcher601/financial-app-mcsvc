package com.nixsolutions.financial.security.aspect;

public @interface JwtProtected
{
  String[] roles();
}

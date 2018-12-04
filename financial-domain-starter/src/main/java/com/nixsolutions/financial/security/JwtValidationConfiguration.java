package com.nixsolutions.financial.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.nixsolutions.financial.security.aspect.JwtSecurityAdvice;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class JwtValidationConfiguration
{
  public JwtSecurityAdvice jwtSecurityAdvice()
  {
    return new JwtSecurityAdvice();
  }
}

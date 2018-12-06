package com.nixsolutions.financial.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.nixsolutions.financial.security.aspect.AuthorizationHeaderMethodParamIdentifier;
import com.nixsolutions.financial.security.aspect.DefaultAuthorizationHeaderMethodParamIdentifier;
import com.nixsolutions.financial.security.aspect.JwtSecurityAdvice;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class JwtValidationConfiguration
{
  @Bean
  public JwtSecurityAdvice jwtSecurityAdvice()
  {
    return new JwtSecurityAdvice();
  }

  @Bean
  public AuthorizationHeaderMethodParamIdentifier authorizationHeaderMethodParamIdentifier()
  {
    return new DefaultAuthorizationHeaderMethodParamIdentifier();
  }
}

package com.nixsolutions.financial.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "financialDomain.security:")
@Getter
@Setter
public class SecurityProperties
{
  private String jwtSecret;

  private Integer expiresIn;

  private Integer passwordHashingStrength;
}

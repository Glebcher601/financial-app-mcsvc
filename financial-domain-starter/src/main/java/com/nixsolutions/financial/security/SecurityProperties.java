package com.nixsolutions.financial.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "financial-domain.security")
@Getter
@Setter
public class SecurityProperties
{
  private String jwtSecret;

  private Integer expiresIn;

  private Integer passwordHashingStrength;
}

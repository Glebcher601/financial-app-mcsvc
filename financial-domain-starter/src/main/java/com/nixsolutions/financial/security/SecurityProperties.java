package com.nixsolutions.financial.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

@Getter
@Setter
class SecurityValues
{
  private String jwtSecret;

  private Integer expiresIn;

  private Integer passwordHashingStrength;
}

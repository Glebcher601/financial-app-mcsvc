package com.nixsolutions.financialjob.configuration;

import com.nixsolutions.financial.security.config.HttpSecurityConfigurationHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class WebSecurityConfig
{
  @Bean
  public SecurityWebFilterChain webFilterChain(HttpSecurityConfigurationHolder securityConfigurationHolder)
  {
    return securityConfigurationHolder.getHttpSecurity()
        .csrf().disable()
        .authorizeExchange().anyExchange().permitAll()
        .and().build();
  }
}
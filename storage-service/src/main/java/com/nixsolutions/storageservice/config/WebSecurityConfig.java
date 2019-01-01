package com.nixsolutions.storageservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class WebSecurityConfig
{
  @Bean
  public SecurityWebFilterChain webFilterChain(/*@Qualifier("preConfiguredHttpSecurity") */ServerHttpSecurity http)
  {
    return http.csrf().disable()
        .authorizeExchange().anyExchange().permitAll()
        .and().build();
  }
}

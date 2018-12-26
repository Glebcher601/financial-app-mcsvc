package com.nixsolutions.storageservice.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSecurityConfig
{
  /*@Bean
  public SecurityWebFilterChain webFilterChain(@Qualifier("preConfiguredHttpSecurity") ServerHttpSecurity http)
  {
    return http.csrf().disable()
        .authorizeExchange().anyExchange().permitAll()
        .and().build();
  }*/
}

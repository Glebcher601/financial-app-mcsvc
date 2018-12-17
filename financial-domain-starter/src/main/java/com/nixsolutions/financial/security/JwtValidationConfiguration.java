package com.nixsolutions.financial.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Deprecated
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.nixsolutions.financial.security")
@EnableConfigurationProperties(SecurityProperties.class)
public class JwtValidationConfiguration
{

}

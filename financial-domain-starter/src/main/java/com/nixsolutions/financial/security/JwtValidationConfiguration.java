package com.nixsolutions.financial.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.nixsolutions.financial.security.aspect.AuthorizationHeaderMethodParamIdentifier;
import com.nixsolutions.financial.security.aspect.DefaultAuthorizationHeaderMethodParamIdentifier;
import com.nixsolutions.financial.security.aspect.JwtSecurityAdvice;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.nixsolutions.financial.security")
public class JwtValidationConfiguration
{

}

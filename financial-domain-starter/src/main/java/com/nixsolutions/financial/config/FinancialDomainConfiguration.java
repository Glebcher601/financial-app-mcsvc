package com.nixsolutions.financial.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.nixsolutions.financial.discovery.ServiceDiscoveryProperties;
import com.nixsolutions.financial.discovery.ServiceRegistry;

@Configuration
@EnableConfigurationProperties(ServiceDiscoveryProperties.class)
@ConditionalOnProperty(prefix = "financialDomain", name = "enableDiscovery", matchIfMissing = true)
public class FinancialDomainConfiguration
{
  @Bean
  public ServiceRegistry serviceRegistry(ServiceDiscoveryProperties discoveryConfig)
  {
    ServiceRegistry serviceRegistry = new ServiceRegistry();
    serviceRegistry.setServiceDiscoveryProperties(discoveryConfig);
    return serviceRegistry;
  }

}

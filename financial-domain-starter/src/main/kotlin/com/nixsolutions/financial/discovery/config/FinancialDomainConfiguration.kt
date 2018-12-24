package com.nixsolutions.financial.discovery.config

import com.nixsolutions.financial.discovery.ServiceDiscoveryProperties
import com.nixsolutions.financial.discovery.ServiceRegistry
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ServiceDiscoveryProperties::class)
@ConditionalOnProperty(prefix = "financialDomain", name = ["enableDiscovery"], matchIfMissing = false)
class FinancialDomainConfiguration {
  @Bean
  fun serviceRegistry(discoveryConfig: ServiceDiscoveryProperties): ServiceRegistry {
    val serviceRegistry = ServiceRegistry()
    serviceRegistry.serviceDiscoveryProperties = discoveryConfig
    return serviceRegistry
  }

}

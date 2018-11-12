package com.nixsolutions.financialjob;

import com.nixsolutions.financial.discovery.ServiceDiscoveryConfig;
import com.nixsolutions.financial.discovery.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@SpringBootApplication
@EnableConfigurationProperties
public class FinancialJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialJobApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "services")
	@Validated
	public ServiceDiscoveryConfig serviceDiscoveryConfig()
	{
		return new ServiceDiscoveryConfig();
	}

	@Bean
	@Autowired
	public ServiceRegistry serviceRegistry(ServiceDiscoveryConfig discoveryConfig)
	{
		return ServiceRegistry.create(discoveryConfig);
	}
}

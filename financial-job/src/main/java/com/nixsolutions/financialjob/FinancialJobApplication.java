package com.nixsolutions.financialjob;

import com.nixsolutions.financial.metrics.CommonMetricsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.nixsolutions.financial.discovery.config.EnableSimpleDiscovery;
import com.nixsolutions.financial.security.config.EnableJwtProtection;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableSimpleDiscovery
@EnableJwtProtection
@Import(CommonMetricsConfig.class)
public class FinancialJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialJobApplication.class, args);
	}

}

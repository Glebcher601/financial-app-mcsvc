package com.nixsolutions.financialjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.nixsolutions.financial.discovery.config.EnableSimpleDiscovery;
import com.nixsolutions.financial.security.config.EnableJwtProtection;

@SpringBootApplication
@EnableSimpleDiscovery
@EnableJwtProtection(useDefault = false)
public class FinancialJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialJobApplication.class, args);
	}

}

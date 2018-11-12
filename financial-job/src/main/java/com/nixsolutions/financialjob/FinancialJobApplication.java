package com.nixsolutions.financialjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.nixsolutions.financial.config.EnableSimpleDiscovery;

@SpringBootApplication
@EnableSimpleDiscovery
public class FinancialJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialJobApplication.class, args);
	}

}

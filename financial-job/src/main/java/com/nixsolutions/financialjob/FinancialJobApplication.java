package com.nixsolutions.financialjob;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.nixsolutions.financialjob.configuration.ApiUrlConfiguration;

@SpringBootApplication
@EnableConfigurationProperties
public class FinancialJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialJobApplication.class, args);
	}
}

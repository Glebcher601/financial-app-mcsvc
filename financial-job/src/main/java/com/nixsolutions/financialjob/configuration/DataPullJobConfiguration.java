package com.nixsolutions.financialjob.configuration;

import static com.nixsolutions.financialjob.configuration.ApiUrlConfiguration.UriParams.API_KEY;
import static com.nixsolutions.financialjob.configuration.ApiUrlConfiguration.UriParams.FUNCTION;
import static com.nixsolutions.financialjob.configuration.ApiUrlConfiguration.UriParams.INTERVAL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataPullJobConfiguration
{
  @Bean
  public String prefilledTemplateUrl(ApiUrlConfiguration apiProps)
  {
    return apiProps.getTemplateUrl()
        .replace("{"+API_KEY+"}", apiProps.getKey())
        .replace("{"+FUNCTION+"}", apiProps.getFunction())
        .replace("{"+INTERVAL+"}", apiProps.getInterval());
  }

  /*@Bean
  Job dataPull(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory)
  {
    return null;
  }*/

}

package com.nixsolutions.financialjob.configuration;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "job.properties.api")
public class ApiUrlConfiguration
{
  public static class UriParams {
    public static final String FUNCTION = "function";
    public static final String API_KEY = "apikey";
    public static final String INTERVAL = "interval";
    public static final String SYMBOL = "symbol";
    public static final String OUTPUT_SIZE = "outputsize";
  }

  private String templateUrl;

  private String key;

  private List<String> symbols;

  private String interval;

  private String function;
}

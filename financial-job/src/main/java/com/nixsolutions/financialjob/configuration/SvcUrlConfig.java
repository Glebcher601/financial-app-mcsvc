package com.nixsolutions.financialjob.configuration;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.nixsolutions.financial.discovery.DiscoveryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "services")
@Component
public class SvcUrlConfig
{
  private List<ServiceRecord> list;

  @ConfigurationProperties(prefix = "services.list")
  public static class ServiceRecord
  {
    private String name;

    private DiscoveryType resolution;

    private String version;

    private String port;

    public String getName()
    {
      return name;
    }

    public void setName(String name)
    {
      this.name = name;
    }

    public DiscoveryType getResolution()
    {
      return resolution;
    }

    public void setResolution(DiscoveryType resolution)
    {
      this.resolution = resolution;
    }

    public String getVersion()
    {
      return version;
    }

    public void setVersion(String version)
    {
      this.version = version;
    }

    public String getPort()
    {
      return port;
    }

    public void setPort(String port)
    {
      this.port = port;
    }
  }

  @Bean
  public String someBean()
  {
    return "STR";
  }

}

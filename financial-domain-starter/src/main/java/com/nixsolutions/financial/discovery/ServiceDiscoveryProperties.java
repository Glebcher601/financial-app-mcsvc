package com.nixsolutions.financial.discovery;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties(prefix = "services")
@Validated
public class ServiceDiscoveryProperties
{
  private List<ServiceRecord> list = new ArrayList<>();
}

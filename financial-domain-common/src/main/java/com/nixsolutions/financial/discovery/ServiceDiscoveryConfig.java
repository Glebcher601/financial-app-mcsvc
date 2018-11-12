package com.nixsolutions.financial.discovery;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ServiceDiscoveryConfig
{
  private List<ServiceRecord> list = new ArrayList<>();
}

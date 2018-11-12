package com.nixsolutions.financial.discovery;

import static com.nixsolutions.financial.discovery.DiscoveryType.LOCAL;

public class ServiceRegistry
{
  private static final String HTTP_SCHEME = "http://";
  private static final String LOCALHOST = "localhost:";
  private ServiceDiscoveryConfig serviceDiscoveryConfig;

  private ServiceRegistry(ServiceDiscoveryConfig serviceDiscoveryConfig)
  {
    this.serviceDiscoveryConfig = serviceDiscoveryConfig;
  }

  public static ServiceRegistry create(ServiceDiscoveryConfig serviceDiscoveryConfig)
  {
    return new ServiceRegistry(serviceDiscoveryConfig);
  }

  public String getServiceUrl(String svcName)
  {
    return serviceDiscoveryConfig.getList().stream()
        .filter(svc -> svc.getName().equals(svcName))
        .findAny()
        .map(this::serviceRecordToUrl)
        .orElseThrow(DiscoveryValidationException::new);
  }

  private String serviceRecordToUrl(ServiceRecord serviceRecord)
  {
    DiscoveryType resolution = serviceRecord.getResolution();
    String port = serviceRecord.getPort();

    if (resolution.equals(LOCAL))
    {
      return HTTP_SCHEME + LOCALHOST + port;
    } else
    {
      return HTTP_SCHEME + serviceRecord.getName() + port;
    }
  }
}

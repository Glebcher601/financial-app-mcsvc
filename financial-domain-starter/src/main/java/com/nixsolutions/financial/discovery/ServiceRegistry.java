package com.nixsolutions.financial.discovery;

import static com.nixsolutions.financial.discovery.DiscoveryType.LOCAL;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRegistry
{
  private static final String HTTP_SCHEME = "http://";
  private static final String LOCALHOST = "localhost:";
  private ServiceDiscoveryProperties serviceDiscoveryProperties;

  public String getServiceUrl(String svcName)
  {
    return serviceDiscoveryProperties.getList().stream()
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

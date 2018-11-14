package com.nixsolutions.financial.discovery;

import static com.nixsolutions.financial.discovery.DiscoveryType.LOCAL;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRegistry
{

  public static final String COLON = ":";

  public static class Services
  {
    public static final String STORAGE = "storage";
    public static final String UI = "ui";
    public static final String PREDICTION = "prediction";
    public static final String USER_AUTH = "userAuth";
  }

  private static final String HTTP_SCHEME = "http://";
  private static final String LOCALHOST = "localhost";
  private ServiceDiscoveryProperties serviceDiscoveryProperties;

  public String getServiceUrl(String svcName)
  {
    return serviceDiscoveryProperties.getList().stream()
        .filter(svc -> svc.getName().equals(svcName))
        .findAny()
        .map(this::serviceRecordToUrl)
        .orElseThrow(DiscoveryException::new);
  }

  private String serviceRecordToUrl(ServiceRecord serviceRecord)
  {
    DiscoveryType resolution = serviceRecord.getResolution();
    String port = serviceRecord.getPort();

    if (resolution.equals(LOCAL))
    {
      return HTTP_SCHEME + LOCALHOST + COLON + port;
    }
    else
    {
      return HTTP_SCHEME + serviceRecord.getName() + COLON + port;
    }
  }
}

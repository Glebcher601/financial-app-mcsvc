package com.nixsolutions.financial.discovery;

public class DiscoveryException extends RuntimeException
{
  public DiscoveryException()
  {
    super("Service not found");
  }

  public DiscoveryException(String message)
  {
    super(message);
  }
}

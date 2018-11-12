package com.nixsolutions.financial.discovery;

public class DiscoveryValidationException extends RuntimeException
{
  public DiscoveryValidationException()
  {
    super("Service definition is wrong at some point");
  }

  public DiscoveryValidationException(String message)
  {
    super(message);
  }
}

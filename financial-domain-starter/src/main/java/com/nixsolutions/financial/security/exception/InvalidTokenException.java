package com.nixsolutions.financial.security.exception;

public class InvalidTokenException extends RuntimeException
{
  public InvalidTokenException()
  {
    this("Token has invalid structure or is counterfeit");
  }

  public InvalidTokenException(String s)
  {
    super(s);
  }

  public InvalidTokenException(String s, Throwable throwable)
  {
    super(s, throwable);
  }

  public InvalidTokenException(Throwable throwable)
  {
    super(throwable);
  }
}

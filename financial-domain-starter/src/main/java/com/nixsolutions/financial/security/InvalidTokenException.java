package com.nixsolutions.financial.security;

public class InvalidTokenException extends RuntimeException
{
  public InvalidTokenException()
  {
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

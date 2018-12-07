package com.nixsolutions.financial.security.exception;

public class NoAccessException extends RuntimeException
{
  public NoAccessException()
  {
    this("No acces to the resource");
  }

  public NoAccessException(String s)
  {
    super(s);
  }

  public NoAccessException(String s, Throwable throwable)
  {
    super(s, throwable);
  }

  public NoAccessException(Throwable throwable)
  {
    super(throwable);
  }
}
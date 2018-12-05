package com.nixsolutions.financial.security.exception;

public class TokenExpiredException extends RuntimeException
{
  public TokenExpiredException()
  {
  }

  public TokenExpiredException(String s)
  {
    super(s);
  }

  public TokenExpiredException(String s, Throwable throwable)
  {
    super(s, throwable);
  }

  public TokenExpiredException(Throwable throwable)
  {
    super(throwable);
  }
}

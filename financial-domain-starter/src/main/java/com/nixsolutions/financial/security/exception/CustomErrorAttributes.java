package com.nixsolutions.financial.security.exception;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class CustomErrorAttributes<T extends  Throwable> extends DefaultErrorAttributes
{
  private static final List<Class<? extends Exception>> SECURITY_EXCEPTIONS = Arrays.asList(
      InvalidTokenException.class,
      NoAccessException.class,
      TokenExpiredException.class
  );

  @Override
  public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace)
  {
    Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
    errorAttributes.remove("trace");

    if(SECURITY_EXCEPTIONS.contains(getError(request).getClass()))
    {
      errorAttributes.put("status", HttpStatus.FORBIDDEN.value());
      errorAttributes.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
      return errorAttributes;
    }

    return errorAttributes;
  }
}

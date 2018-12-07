package com.nixsolutions.financial.security.aspect;

import static com.nixsolutions.financial.security.SecurityConstants.BEARER_TYPE;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.nixsolutions.financial.security.JwtVerifier;
import com.nixsolutions.financial.security.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;

/**
 * We should have @RequestHeader parameter for aspect to fire
 */
@Aspect
@Component
public class JwtSecurityAdvice
{
  private final AuthorizationHeaderMethodParamIdentifier authHeaderMethodParamIdentifier;
  private final JwtVerifier jwtVerifier;

  @Autowired
  public JwtSecurityAdvice(AuthorizationHeaderMethodParamIdentifier authHeaderMethodParamIdentifier, JwtVerifier
      jwtVerifier)
  {
    this.authHeaderMethodParamIdentifier = authHeaderMethodParamIdentifier;
    this.jwtVerifier = jwtVerifier;
  }

  @Around("@annotation(com.nixsolutions.financial.security.aspect.JwtProtected)")
  public Object secureInvocation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
  {
    final Object invocationResult;
    final Method method = getMethod(proceedingJoinPoint);
    final Object originalObject = proceedingJoinPoint.getTarget();
    final Object[] args = proceedingJoinPoint.getArgs();

    Claims claims = getAuthorizationHeader(method.getParameters(), args)
        .map(jwtVerifier::parseToken)
        .orElseThrow(InvalidTokenException::new);

    String[] roles = method.getAnnotation(JwtProtected.class).roles();
    jwtVerifier.hasAccess(claims, roles).throwUnderlyingException();

    return proceedingJoinPoint.proceed(args);
  }

  private Optional<String> getAuthorizationHeader(Parameter[] parameters, Object[] args)
  {
    Map<Parameter, Object> params = IntStream.range(0, parameters.length)
        .boxed()
        .collect(Collectors.toMap(i -> parameters[i], i -> args[i]));

    return params.entrySet().stream()
        .filter(entry -> authHeaderMethodParamIdentifier.test(entry.getKey()))
        .findAny()
        .map(entry -> entry.getValue().toString())
        .map(headerValue -> headerValue.substring(BEARER_TYPE.length(), headerValue.length()));
  }

  private Method getMethod(ProceedingJoinPoint joinPoint)
  {
    return ((MethodSignature) joinPoint.getSignature()).getMethod();
  }
}

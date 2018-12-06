package com.nixsolutions.financial.security.aspect;

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

/**
 * We should have @RequestHeader parameter for aspect to fire
 */
@Aspect
public class JwtSecurityAdvice
{
  private final AuthorizationHeaderMethodParamIdentifier authHeaderMethodParamIdentifier;

  @Autowired
  public JwtSecurityAdvice(AuthorizationHeaderMethodParamIdentifier authHeaderMethodParamIdentifier)
  {
    this.authHeaderMethodParamIdentifier = authHeaderMethodParamIdentifier;
  }

  @Around("@annotation(com.nixsolutions.financial.security.aspect.JwtProtected)")
  public Object secureInvocation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
  {
    final Object invocationResult;
    final Method method = getMethod(proceedingJoinPoint);
    final Object originalObject = proceedingJoinPoint.getTarget();
    final Object[] args = proceedingJoinPoint.getArgs();
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
        .map(entry -> entry.getValue().toString());
  }

  private Method getMethod(ProceedingJoinPoint joinPoint)
  {
    return ((MethodSignature) joinPoint.getSignature()).getMethod();
  }
}

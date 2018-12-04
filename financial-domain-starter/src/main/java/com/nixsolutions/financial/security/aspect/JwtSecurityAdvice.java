package com.nixsolutions.financial.security.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class JwtSecurityAdvice
{
  @Around("@annotation(com.nixsolutions.financial.security.aspect.JwtProtected)")
  public Object secureInvocation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
  {
    final Object invocationResult;
    final Method method = getMethod(proceedingJoinPoint);
    final Object originalObject = proceedingJoinPoint.getTarget();
    final Object[] args = proceedingJoinPoint.getArgs();

    return "";
  }

  private Method getMethod(ProceedingJoinPoint joinPoint)
  {
    return ((MethodSignature) joinPoint.getSignature()).getMethod();
  }
}

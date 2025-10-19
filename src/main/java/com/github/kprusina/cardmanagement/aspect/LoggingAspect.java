package com.github.kprusina.cardmanagement.aspect;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  private static final String LOGGABLE_METHODS =
      "execution(* com.github.kprusina.cardmanagement.feature..*(..))";

  @Around(LOGGABLE_METHODS)
  public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().toShortString();
    Object[] args = joinPoint.getArgs();

    if (log.isTraceEnabled()) {
      log.trace("Entering {} with args={}", methodName, maskSensitiveArgs(args));
    }

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    Object result;
    try {
      result = joinPoint.proceed();
    } catch (Exception e) {
      log.error("Exception in {} with args={}", methodName, maskSensitiveArgs(args), e);
      throw e;
    }

    stopWatch.stop();

    if (log.isTraceEnabled()) {
      log.trace(
          "Exiting {} with result={} (took {} ms)",
          methodName,
          maskResult(result),
          stopWatch.getTotalTimeMillis());
    }

    return result;
  }

  private Object[] maskSensitiveArgs(Object[] args) {
    if (args == null) return null;
    Object[] masked = Arrays.copyOf(args, args.length);
    for (int i = 0; i < masked.length; i++) {
      if (masked[i] instanceof String && isSensitive((String) masked[i])) {
        masked[i] = "***";
      }
    }
    return masked;
  }

  private boolean isSensitive(String value) {
    return value.toLowerCase().contains("password") || value.toLowerCase().contains("token");
  }

  private Object maskResult(Object result) {
    if (result == null) return null;
    if (result instanceof String && ((String) result).length() > 100) {
      return ((String) result).substring(0, 100) + "...";
    }
    return result;
  }
}

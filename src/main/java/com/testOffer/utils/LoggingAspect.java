package com.testOffer.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

  @Before("@annotation(com.testOffer.utils.LogMethod)")
  public void logMethodExecution(JoinPoint joinPoint) {
    String method = joinPoint.getSignature().getName();
    String params = Arrays.toString(joinPoint.getArgs());
    LOGGER.info("Method [" + method + "] gets called with parameters " + params);
  }

  @Around("@annotation(com.testOffer.utils.LogMethod)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis(); 
    Object proceed = joinPoint.proceed();
    long duration = System.currentTimeMillis() - startTime;
    LOGGER.info("Execution took [" + duration + "ms]");
    return proceed;
  }

}

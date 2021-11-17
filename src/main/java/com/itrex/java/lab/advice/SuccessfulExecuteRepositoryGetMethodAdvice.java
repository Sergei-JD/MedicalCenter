package com.itrex.java.lab.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SuccessfulExecuteRepositoryGetMethodAdvice {

    private static final String SUCCESS_MESSAGE = "This method: %s completed successfully";

    @Pointcut("execution(* com.itrex.java.lab.persistence.hibernateimpl.*.get*(..))")
    public void getMethodInThePersistenceLayer() {
        // Do nothing
    }

    @Around(value = "getMethodInThePersistenceLayer()")
    public Object successfulResultOfExecutingRepositoryMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        log.info(String.format(SUCCESS_MESSAGE, joinPoint.getSignature().getName()));
        log.info(result.toString());

        return result;
    }

}

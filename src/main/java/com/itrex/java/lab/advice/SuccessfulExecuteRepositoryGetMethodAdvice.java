package com.itrex.java.lab.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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

    @AfterReturning(value = "getMethodInThePersistenceLayer()", returning = "entity")
    public void successfulResultOfExecutingRepositoryMethod(JoinPoint joinPoint, Object entity) {

        log.info(String.format(SUCCESS_MESSAGE, joinPoint.getSignature().getName()));
        log.info(entity.toString());
    }

}

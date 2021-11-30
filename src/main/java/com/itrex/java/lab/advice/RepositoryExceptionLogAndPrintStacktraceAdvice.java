package com.itrex.java.lab.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RepositoryExceptionLogAndPrintStacktraceAdvice {

    private static final String EXCEPTION_MESSAGE_LOG =
            "This method broke: %s with the message: %s";

    @AfterThrowing(value = "execution(* com.itrex.java.lab.persistence.hibernateimpl.*.*(..))",
            throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {

        log.error(String.format(EXCEPTION_MESSAGE_LOG, joinPoint.getSignature().getName(),
                ex.getMessage()), ex);
    }

}

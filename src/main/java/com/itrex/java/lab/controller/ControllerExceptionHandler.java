package com.itrex.java.lab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import com.itrex.java.lab.dto.ExceptionDTO;
import com.itrex.java.lab.exception.CustomAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.NotAcceptableStatusException;

import io.jsonwebtoken.JwtException;
import org.webjars.NotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private final HttpServletRequest request;

    @Autowired
    public ControllerExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionDTO maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
        ControllerExceptionHandler.LOG.error("Bad Request ", e.getCause());
        return this.initExceptionDTO(e, "Max Upload Size Exceeded", 400, "Bad Request");
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ExceptionDTO customExceptionHandler(CustomAuthenticationException e) {
        ControllerExceptionHandler.LOG.error(e.getMessage());
        return this.initExceptionDTO(e, e.getMessage(), 401, "Bad Request");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ExceptionDTO accessDeniedException(AccessDeniedException e) {
        ControllerExceptionHandler.LOG.error("Unauthorized ", e);
        return this.initExceptionDTO(e, e.getMessage(), 401, "Unauthorized");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ExceptionDTO authenticationException(AuthenticationException e) {
        ControllerExceptionHandler.LOG.error("Forbidden ", e);
        return this.initExceptionDTO(e, e.getMessage(), 403, "Forbidden");
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ExceptionDTO jwtException(JwtException e) {
        ControllerExceptionHandler.LOG.error("Forbidden ", e);
        return this.initExceptionDTO(e, e.getMessage(), 403, "Forbidden");
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ExceptionDTO notFoundException(NotFoundException e) {
        ControllerExceptionHandler.LOG.error("Not Found ", e);
        return this.initExceptionDTO(e, e.getMessage(), 404, "Not Found");
    }

    @ExceptionHandler(NotAcceptableStatusException.class)
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public ExceptionDTO notAcceptableStatusException(NotAcceptableStatusException e) {
        ControllerExceptionHandler.LOG.error("Not Acceptable ", e);
        return this.initExceptionDTO(e, e.getMessage(), 406, "Not Acceptable");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO exception(Exception e) {
        ControllerExceptionHandler.LOG.error("Internal Server error ", e);
        return this.initExceptionDTO(e, "Internal Error", 500, "Internal Server error");
    }

    private ExceptionDTO initExceptionDTO(Exception e, String message, int status, String error) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setTimestamp(LocalDateTime.now().toString());
        exceptionDTO.setStatus(status);
        exceptionDTO.setError(error);
        String path = (String) this.request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        exceptionDTO.setPath(path);
        exceptionDTO.setMessage(message);

        return exceptionDTO;
    }

}

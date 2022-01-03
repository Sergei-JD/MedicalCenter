package com.itrex.java.lab.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    private final String msg;

    public CustomAuthenticationException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}

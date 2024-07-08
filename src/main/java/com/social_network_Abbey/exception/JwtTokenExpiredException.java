package com.social_network_Abbey.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenExpiredException extends AuthenticationException {
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}

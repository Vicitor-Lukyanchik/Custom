package com.senla.social.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}

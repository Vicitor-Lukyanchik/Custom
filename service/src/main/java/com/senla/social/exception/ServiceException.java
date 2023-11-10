package com.senla.social.exception;

/**
 * If will be some exception in service layer
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}

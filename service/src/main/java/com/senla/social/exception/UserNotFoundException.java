package com.senla.social.exception;

/**
 * If user have not founded
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}

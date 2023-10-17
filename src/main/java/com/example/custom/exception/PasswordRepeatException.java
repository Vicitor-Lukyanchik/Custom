package com.example.custom.exception;

public class PasswordRepeatException extends Exception {

    public PasswordRepeatException(String message) {
        super(message);
    }
}

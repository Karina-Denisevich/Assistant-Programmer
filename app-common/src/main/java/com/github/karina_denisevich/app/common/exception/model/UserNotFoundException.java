package com.github.karina_denisevich.app.common.exception.model;


public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2967357473314163159L;

    public UserNotFoundException(String message) {
        super(message);
    }
}

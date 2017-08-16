package com.github.karina_denisevich.app.common.exception.model;


public class DuplicateEntityException  extends RuntimeException {

    private static final long serialVersionUID = -865813185989877602L;

    public DuplicateEntityException(String message) {
        super(message);
    }

}

package com.mithpath.backend.exception;

import lombok.Getter;

@Getter
public class UsernameAlreadyExistsException extends RuntimeException {

    private final String username;

    public UsernameAlreadyExistsException(String username, String message) {
        super(message);
        this.username = username;
    }
}

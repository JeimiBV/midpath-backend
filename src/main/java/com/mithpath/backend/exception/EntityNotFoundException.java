package com.mithpath.backend.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entity, Integer id) {
        super(MessageUtil.getProperty("exception.entityNotFound.message", entity, id.toString()));
    }
}

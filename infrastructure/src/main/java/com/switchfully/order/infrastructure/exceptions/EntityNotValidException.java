package com.switchfully.order.infrastructure.exceptions;

public class EntityNotValidException extends RuntimeException {

    public EntityNotValidException(String additionalContext, Object entity) {
        super("During " + additionalContext + ", the following entity was found to be invalid: "
                + entity.toString());
    }
}

package com.switchfully.order.infrastructure.exceptions;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(String additionalContext) {
        super(additionalContext);
    }
}

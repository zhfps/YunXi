package com.admin.framework.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super();
    }

    public NotFoundUserException(String message) {
        super(message);
    }
}

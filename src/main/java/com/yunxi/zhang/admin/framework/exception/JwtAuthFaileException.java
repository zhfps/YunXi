package com.yunxi.zhang.admin.framework.exception;

import java.io.IOException;

public class JwtAuthFaileException extends IOException {
    public JwtAuthFaileException() {
        super();
    }

    public JwtAuthFaileException(String message) {
        super(message);
    }
}

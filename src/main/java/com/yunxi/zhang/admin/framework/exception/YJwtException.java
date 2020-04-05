package com.yunxi.zhang.admin.framework.exception;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;

public class YJwtException extends RuntimeException {

    public YJwtException() {
        super();
    }

    public YJwtException(String message) {
        super(message);
    }
}

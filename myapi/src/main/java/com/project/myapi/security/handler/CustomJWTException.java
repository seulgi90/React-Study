package com.project.myapi.security.handler;

public class CustomJWTException extends RuntimeException {
    public CustomJWTException() {
        super("JWT 처리 중 예외가 발생했습니다.");
    }

    public CustomJWTException(String message) {
        super(message);
    }

    public CustomJWTException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomJWTException(Throwable cause) {
        super("JWT 처리 중 예외가 발생했습니다.", cause);
    }
}
package com.project.myapi.security.handler;

import java.util.Map;

public class CustomJWTException extends RuntimeException {

    private final boolean expired;
    private final Map<String, Object> claims;

    //  만료 여부 + claims + 메시지 + 원인
    public CustomJWTException(String message, boolean expired, Map<String, Object> claims, Throwable cause) {
        super(message, cause);
        this.expired = expired;
        this.claims = claims;
    }

    // 만료된 경우 전용 생성자 (claims 포함)
    public CustomJWTException(Map<String, Object> claims, Throwable cause) {
        super("JWT 만료됨", cause);
        this.expired = true;
        this.claims = claims;
    }

    // 기본적인 메시지 + 일반 예외
    public CustomJWTException(String message, Throwable cause) {
        super(message, cause);
        this.expired = false;
        this.claims = null;
    }

    // 간단 메시지 전용
    public CustomJWTException(String message) {
        super(message);
        this.expired = false;
        this.claims = null;
    }

    // 디폴트 예외
    public CustomJWTException() {
        super("JWT 처리 중 예외가 발생했습니다");
        this.expired = false;
        this.claims = null;
    }

    public boolean isExpired() {
        return expired;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }
}
package com.project.myapi.security.filter;

import com.project.myapi.security.handler.CustomJWTException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.*;

import java.io.IOException;

@Log4j2
public class JwtExceptionFilter extends GenericFilterBean {
    // JWT 검증 예외를 종류별로 나눠 처리
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            chain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            log.warn("AccessDeniedException", e);
            request.setAttribute("exception", "ACCESS_DENIED");
        } catch (ExpiredJwtException e) {
            log.warn("ExpiredJwtException", e);
            request.setAttribute("exception", "EXPIRED_TOKEN");
        } catch (MalformedJwtException e) {
            log.warn("MalformedJwtException", e);
            request.setAttribute("exception", "MALFORMED_TOKEN");
        } catch (SecurityException e) {
            log.warn("SecurityException (Invalid Signature)", e);
            request.setAttribute("exception", "INVALID_SIGNATURE");
        } catch (UnsupportedJwtException e) {
            log.warn("UnsupportedJwtException", e);
            request.setAttribute("exception", "UNSUPPORTED_TOKEN");
        } catch (IllegalArgumentException e) {
            log.warn("IllegalArgumentException", e);
            request.setAttribute("exception", "ILLEGAL_ARGUMENT");
        } catch (JwtException e) {
            log.warn("JwtException", e);
            request.setAttribute("exception", "JWT_UNKNOWN_ERROR");
        } catch (CustomJWTException e) {
            log.warn("CustomJWTException", e);
            request.setAttribute("exception", e.getMessage()); // 커스텀 메시지 전달
        }
    }
}

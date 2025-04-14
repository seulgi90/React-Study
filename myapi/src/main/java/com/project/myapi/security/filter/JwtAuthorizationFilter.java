package com.project.myapi.security.filter;

import com.google.gson.Gson;
import com.project.myapi.security.JwtProvider;
import com.project.myapi.security.handler.CustomJWTException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class JwtAuthorizationFilter extends OncePerRequestFilter { // OncePerRequestFilter를 상속받아 요청당 한 번만 이 필터가 실행되도록 한다

    private final JwtProvider jwtProvider;

    public JwtAuthorizationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    // 어떤 경로로 들어오면 검사를해야되고 , 안해야되고(로그인의 경우 토큰이 없으니까!)
    // true : 필터 동작 안함,  false :  필터 동작(체크)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getRequestURI();

        // 체크 제외
        return path.equals("/api/login") ||
                path.equals("/api/login/refresh") ||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("-------------doFilterInternal-------------");

        try {
            String token = request.getHeader("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                throw new CustomJWTException("유효하지 않은 Authorization 헤더입니다.");
            }

            token = token.substring(7); // "Bearer " 제거 후 토큰 값만 전달
            UsernamePasswordAuthenticationToken auth = jwtProvider.getAuthentication(token); // UserDetsils, Password, Role -> 접근권한 인증 Token 생성


            if (auth == null) {
                throw new CustomJWTException("JWT 토큰 인증 실패");
            }

            Authentication authentication = auth;
            SecurityContextHolder.getContext().setAuthentication(authentication); //현재 Request의 Security Context에 접근권한 설정
            filterChain.doFilter(request, response);

        } catch (CustomJWTException e) {
            log.warn("JWT 인증 실패: {}", e.getMessage());
            sendErrorResponse(response, e.getMessage()); // ← 예외 메시지 그대로 클라이언트에 전달
        }
        catch (Exception e) {
            log.error("알 수 없는 인증 오류 발생", e);
            sendErrorResponse(response, "JWT 인증 중 내부 오류가 발생했습니다.");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> error = new HashMap<>();
        error.put("error", true);
        error.put("message", message);

        String jsonResponse = new Gson().toJson(error);
        PrintWriter writer = response.getWriter();
        writer.println(jsonResponse);
        writer.close();
    }




}

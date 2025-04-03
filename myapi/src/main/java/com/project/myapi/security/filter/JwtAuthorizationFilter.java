package com.project.myapi.security.filter;

import com.google.gson.Gson;
import com.project.myapi.domain.Member;
import com.project.myapi.dto.MemberDTO;
import com.project.myapi.security.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
        return path.equals("/login") ||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("-------------doFilterInternal-------------");

        try {
            String token = request.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // "Bearer " 제거 후 토큰 값만 전달
            }


            UsernamePasswordAuthenticationToken auth = jwtProvider.getAuthentication(token); // UserDetsils, Password, Role -> 접근권한 인증 Token 생성
            Authentication authentication = auth;
            SecurityContextHolder.getContext().setAuthentication(authentication); //현재 Request의 Security Context에 접근권한 설정
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.info("------JwtAuthorizationFilter Error ");

            Gson gson = new Gson();
            Map<String, Object> error = new HashMap<>();
            error.put("error", true); // todo 에러 코드 정리 필요
            error.put("message", "JwtAuthorizationFilter Error");
            String jsonResponse = gson.toJson(error);

            response.setContentType("application/json");

            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonResponse);
            printWriter.close();
        }

    }


}

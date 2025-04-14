package com.project.myapi.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@Log4j2
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // 로그인 없이 API 요청, JWT가 없거나 검증 실패한 경우, SecurityContextHolder에 인증 정보가 없는 경우
    // CustomAuthenticationEntryPoint 가 없으면, 토큰이 없을 때 에러가 무시되거나, 불필요한 리다이렉션이 발생할 수 있음

    // 인증되지 않은 사용자가 보호된 리소스에 접근할 때 호출
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.warn("인증되지 않은 요청 - {}", authException.getMessage());

        // 응답 코드 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json; charset=UTF-8");

        // 응답 내용 구성
        Map<String, Object> result = new HashMap<>();
        result.put("error", true);
        result.put("code", HttpStatus.UNAUTHORIZED.value());
        result.put("message", "인증이 필요한 요청입니다.");

        // JSON 변환 후 출력
        Gson gson = new Gson();
        PrintWriter writer = response.getWriter();
        writer.println(gson.toJson(result));
        writer.close();
    }

}

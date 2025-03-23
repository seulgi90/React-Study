package com.project.mallapi.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.info("Login fail........."+ exception);

        // JSON 변환
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(Map.of("error", "ERROR_LOGIN"));

        // todo 에러 상태 코드 별도 설정 안함
        // HTTP 응답 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // PrintWriter를 사용하여 JSON 응답 반환
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonResponse);
        printWriter.close();
    }
}

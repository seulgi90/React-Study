package com.project.myapi.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class CustomAccessDeniedhandler implements AccessDeniedHandler {

    // AccessDeniedHandler : 인증되었지만 권한이 없는 경우
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {


        // 상태코드 설정
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Gson gson = new Gson();

        Map<String, Object> accessDeniedMsg = new HashMap<>();
        accessDeniedMsg.put("error", true);
        accessDeniedMsg.put("message", "접근 권한이 없습니다.");
        accessDeniedMsg.put("code", HttpStatus.FORBIDDEN.value()); // 403

        String jsonResponse = gson.toJson(accessDeniedMsg);

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonResponse);
        printWriter.close();
    }
}

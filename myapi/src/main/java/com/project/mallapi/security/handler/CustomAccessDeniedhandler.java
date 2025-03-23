package com.project.mallapi.security.handler;

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

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Gson gson = new Gson();

        Map<String, Object> accessDeniedMsg = new HashMap<>();
        accessDeniedMsg.put("error", "ERROR_ACCESSDENIED");
        accessDeniedMsg.put("code", HttpStatus.FORBIDDEN.value());

        String jsonResponse = gson.toJson(accessDeniedMsg);

        response.setContentType("application/json");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonResponse);
        printWriter.close();
    }
}

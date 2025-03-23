package com.project.mallapi.security.handler;

import com.google.gson.Gson;
import com.project.mallapi.dto.MemberDTO;
import com.project.mallapi.dto.Token;
import com.project.mallapi.security.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    public APILoginSuccessHandler(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 인증 성공 후 실행되는 로직 (일반적인 인증 성공 핸들러)
        log.info("-----------------------");
        log.info("------- onAuthenticationSuccess ----");
        log.info("-----------------------");

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal(); // 인증 된 사용자 정보

        Map<String, Object> claims = memberDTO.getClaims(); // MemberDTO에 정의된 getClaims() 호출, 사용자 정보 가져오기

        Token token = jwtProvider.generateToken(claims);
        claims.put("accessToken", token.getAccessToken());
        claims.put("refreshToken", token.getRefreshToken());

        // JSON 변환
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(claims);

        // HTTP 응답 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // PrintWriter를 사용하여 JSON 응답 반환
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonResponse);
        printWriter.close();
    }

//    // 로그인 성공 후, 추가적인 필터 로직이 필요할 때 사용 : chain.doFilter(request, response);
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
//    }
}

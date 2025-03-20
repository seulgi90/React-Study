package com.project.mallapi.security.filter;

import com.google.gson.Gson;
import com.project.mallapi.security.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
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
    // true : 필터 동작 안함,  false :  필터 동작
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getRequestURI();

//        // 토큰이 필요하지 않은 api url 배열 구성
//        List<String> list = Arrays.asList(
//                "/login",  // 로그인 페이지의 URL 추가
//                "/css/**",
//                "/js/**",
//                "/images/**"
//        );
//
//        if (list.contains(path)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        log.info("-------------check uri ===", path);
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("-------------doFilterInternal-------------");

        try {
            String token = request.getHeader("Authorization");
            log.info("------JwtAuthorizationFilter accessToken ----" + token);

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // "Bearer " 제거 후 토큰 값만 전달
            }

            Map<String, Object> claims = jwtProvider.validateToken(token);
            log.info("------JwtAuthorizationFilter claims ----" + claims);

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



        filterChain.doFilter(request, response);
    }


}

package com.project.mallapi.config;

import com.project.mallapi.security.JwtProvider;
import com.project.mallapi.security.filter.JwtAuthorizationFilter;
import com.project.mallapi.security.handler.APILoginFailHandler;
import com.project.mallapi.security.handler.APILoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("-----Security start-----");

        http
                .cors(cors -> {cors.configurationSource(corsConfigurationSource());}) // CORS 설정
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (REST API 사용 시 필수)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(config -> {
            config.loginPage("/login");
            config.successHandler(new APILoginSuccessHandler(jwtProvider)); // 로그인 성공 후 jwt 토큰 발급 및 json 응답 처리 todo 로그인 컨트롤러 생성 시 이동 예정
            config.failureHandler(new APILoginFailHandler());
        });

        http.addFilterBefore(new JwtAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class); // UsernamePasswordAuthenticationFilter 동작 전에 JwtAuthorizationFilter 필터 먼저 실행 되도록 설정
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration(); // CORS 정책 설정

        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 모든 도메인에서 접근 허용
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // 허용할 메서드 지정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // 클라이언트가 서버로 보낼 수 있는 요청 헤더를 지정
        configuration.setAllowCredentials(true); //  인증정보 쿠키, 토큰 포함 가능하도록 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 API 엔드포인트(/**)에 위 설정 적용

        return source;
    }
}

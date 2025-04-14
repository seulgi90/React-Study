package com.project.myapi.security;

import com.project.myapi.security.filter.JwtAuthorizationFilter;
import com.project.myapi.security.filter.JwtExceptionFilter;
import com.project.myapi.security.handler.CustomAccessDeniedhandler;
import com.project.myapi.security.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("-----Security start-----");

        http
            .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (REST API 사용 시 필수)
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
            .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JwtExceptionFilter(), JwtAuthorizationFilter.class) //  JwtAuthorizationFilter에서 예외가 발생하기 전에 JwtExceptionFilter가 먼저 실행되도록 등록
            .addFilterBefore(new JwtAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class) // UsernamePasswordAuthenticationFilter 동작 전에 JwtAuthorizationFilter 필터 먼저 실행 되도록 설정
            .exceptionHandling(ex -> ex
                    .accessDeniedHandler(new CustomAccessDeniedhandler())
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 실패 시 처리
            );

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

package com.project.myapi.controller;

import com.project.myapi.domain.RefreshToken;
import com.project.myapi.dto.LoginDto;
import com.project.myapi.dto.Token;
import com.project.myapi.repository.RefreshTokenRepository;
import com.project.myapi.security.JwtProvider;
import com.project.myapi.service.AuthService;
import com.project.myapi.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final AuthService authService;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        
        // todo id 존재 여부 확인 필요

        // 사용자 인증
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getEmail());
        
        if (!passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "비밀번호가 틀렸습니다."));
        }

        // claims 생성
        Map<String, Object> claims = Map.of(
                "email", userDetails.getUsername(),
                "roleNames", userDetails.getAuthorities().stream().map(Object::toString).toList()
        );

        // 토큰 발급
        Token token = jwtProvider.generateToken(claims);

        // refreshToken 저장
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .email(userDetails.getUsername())
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .build()
        );

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("loginId", userDetails.getUsername());
        result.put("roles", userDetails.getAuthorities().toString());
        result.put("accessToken", token.getAccessToken());
        result.put("refreshToken", token.getRefreshToken());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authHeader, @RequestBody String refreshToken) {
        Map<String, Object> result = authService.validateAndRefreshTokens(authHeader, refreshToken);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

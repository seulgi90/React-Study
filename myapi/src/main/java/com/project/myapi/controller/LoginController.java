package com.project.myapi.controller;

import com.project.myapi.domain.RefreshToken;
import com.project.myapi.dto.LoginDto;
import com.project.myapi.dto.MemberDto;
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
import java.util.stream.Collectors;

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

        // todo 유저의 정보가 늘어날 경우 고려 필요할 듯
        MemberDto memberDto = (MemberDto) userDetails;

        // 토큰 발급용 claims 생성
        Map<String, Object> claims = memberDto.getClaims();
        // claims 생성 -- 유저 정보 담기위
//        Map<String, Object> claims = Map.of(
//                "email", userDetails.getUsername(),
//                "roleNames", userDetails.getAuthorities().stream().map(Object::toString).toList()
//        );

        // 토큰 발급
        Token token = jwtProvider.generateToken(claims);

        // refreshToken 저장
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .email(memberDto.getUsername())
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .build()
        );

        Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("email", memberDto.getUsername());
        userInfo.put("name", memberDto.getName());
        userInfo.put(
                "roles",
                memberDto.getAuthorities().stream()
                        .map(grantedAuthority -> grantedAuthority.getAuthority())
                        .collect(Collectors.toList())
        );

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("userInfo", userInfo);
        result.put("accessToken", token.getAccessToken());
        result.put("refreshToken", token.getRefreshToken());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authHeader, @RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        Map<String, Object> result = authService.validateAndRefreshTokens(authHeader, refreshToken);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

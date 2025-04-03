package com.project.myapi.service;

import com.project.myapi.dto.Token;
import com.project.myapi.security.JwtProvider;
import com.project.myapi.security.handler.CustomJWTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    public Map<String, Object> validateAndRefreshTokens(String authHeader, String refreshToken) {

        if (refreshToken == null) {
            throw new CustomJWTException("Null refreshToken");
        }

        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("Invaild accessToken");
        }

        String accessToken = authHeader.substring(7); // Bearer xxxx... 제거

        if (!jwtProvider.isTokenExpired(accessToken)) {
            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            );
        }

        // refreshToken 검증
        Map<String, Object> claims = jwtProvider.validateRefreshToken(refreshToken);

        // refreshToken 검증
        Date refreshExp = new Date((Integer) claims.get("exp") * 1000L);

        // access만 발급하거나, 둘 다 발급
        Token createdToken = jwtProvider.generateToken(claims);
        Token newTokens = jwtProvider.isExpiringSoon(refreshExp)
                ? createdToken // refreshToken이 1시간 미만이면 둘 다 새로 발급
                : Token.builder()
                .grantType("Bearer")
                .accessToken(createdToken.getAccessToken()) // accessToken만 새로 발급
                .refreshToken(refreshToken) // 기존 refreshToken 유지
                .build();

        return Map.of(
                "accessToken", newTokens.getAccessToken(),
                "refreshToken", newTokens.getRefreshToken()
        );
    }
}

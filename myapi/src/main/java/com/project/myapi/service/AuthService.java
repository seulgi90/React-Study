package com.project.myapi.service;

import com.project.myapi.domain.RefreshToken;
import com.project.myapi.dto.Token;
import com.project.myapi.repository.RefreshTokenRepository;
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
    private final RefreshTokenRepository refreshTokenRepository;

    public Map<String, Object> validateAndRefreshTokens(String authHeader, String refreshToken) {

        if (refreshToken == null) {
            throw new CustomJWTException("Null refreshToken");
        }

        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("Invaild accessToken");
        }

        String accessToken = authHeader.substring(7); // Bearer xxxx... 제거

        // accessToken이 아직 유효하면 DB에 저장된 최신 accessToken 반환 가능
        if (!jwtProvider.isTokenExpired(accessToken)) {
            String email = (String) jwtProvider.validateAccessToken(accessToken).get("email");
            RefreshToken latest = refreshTokenRepository.findById(email)
                    .orElseThrow(() -> new CustomJWTException("DB에 저장된 accessToken 없음"));

            return Map.of(
                    "accessToken", latest.getAccessToken(),
                    "refreshToken", latest.getRefreshToken()
            );
        }

        // refreshToken 검증
        Map<String, Object> claims = jwtProvider.validateRefreshToken(refreshToken);
        String email = (String) claims.get("email");

        // DB에서 refreshToken 확인
        RefreshToken savedToken = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new CustomJWTException("DB에 저장된 리프레시 토큰 없음"));


        if (!savedToken.getRefreshToken().equals(refreshToken)) {
            throw new CustomJWTException("DB에 저장된 refreshToken과 일치하지 않음");
        }

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

        // DB 갱신
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .email(email)
                        .accessToken(newTokens.getAccessToken())
                        .refreshToken(newTokens.getRefreshToken())
                        .build()
        );

        return Map.of(
                "accessToken", newTokens.getAccessToken(),
                "refreshToken", newTokens.getRefreshToken()
        );
    }
}

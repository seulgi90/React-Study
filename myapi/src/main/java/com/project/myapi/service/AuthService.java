package com.project.myapi.service;

import com.project.myapi.domain.RefreshToken;
import com.project.myapi.dto.Token;
import com.project.myapi.repository.RefreshTokenRepository;
import com.project.myapi.security.JwtProvider;
import com.project.myapi.security.handler.CustomJWTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public Map<String, Object> validateAndRefreshTokens(String authHeader, String refreshToken) {
        log.info("-------------- AuthService >  validateAndRefreshTokens");

        if (refreshToken == null) {
            throw new CustomJWTException("Null refreshToken");
        }

        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("Invaild accessToken");
        }

        String accessToken = authHeader.substring(7); // Bearer xxxx... 제거

        // accessToken이 만료되지 않았다면 최신 accessToken 반환
        if (!jwtProvider.isTokenExpired(accessToken)) {
            RefreshToken latest = refreshTokenRepository.findByAccessToken(accessToken)
                    .orElseThrow(() -> new CustomJWTException("DB에 저장된 accessToken 없음"));

            return Map.of(
                    "accessToken", latest.getAccessToken(),
                    "refreshToken", latest.getRefreshToken()
            );
        }

        // refreshToken 검증
        Map<String, Object> claims = jwtProvider.validateRefreshToken(refreshToken);
        log.info("----------------- refreshToken 검증 결과: {}", claims);

        String email = (String) claims.get("email");

        // DB에서 refreshToken 확인
        RefreshToken savedToken = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new CustomJWTException("DB에 저장된 리프레시 토큰 없음"));

        // 요청한 사용자의 email 기준으로 DB에 저장된 refreshToken을 조회한 뒤,
        // 클라이언트가 전달한 refreshToken과 비교하여 토큰의 소유자가 실제 사용자와 일치하는지 검증
        if (!savedToken.getRefreshToken().equals(refreshToken)) {
            throw new CustomJWTException("DB에 저장된 refreshToken과 일치하지 않음");
        }

        // refreshToken 만료 시간
        Date refreshExp = new Date(((Number) claims.get("exp")).longValue() * 1000L);

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

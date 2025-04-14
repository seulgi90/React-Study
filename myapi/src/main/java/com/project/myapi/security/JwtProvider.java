package com.project.myapi.security;

import com.project.myapi.dto.Token;
import com.project.myapi.security.handler.CustomJWTException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


@Component
public class JwtProvider {

    private final UserDetailsService userDetailsService;

    private static final String SECRET_KEY = "VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9XkHa";
    private static final String REFRESH_KEY = "V2pkQmZxS3NzTG5ZdXRXak5lZFlYSGIwc0xOZWZXVFE=";
//    private static final long ACCESS_TOKEN_EXP_TIME = 1000 * 60 * 60; // 1시간
    private static final long ACCESS_TOKEN_EXP_TIME =  1000 * 10; // 10초 토큰 테스트 위함
    private static final long REFRESH_TOKEN_EXP_TIME = 1000 * 60 * 60 * 24; // 24시간

    private final SecretKey secretKey;
    private final SecretKey refreshKey;

    public JwtProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)); // todo 추후 @Value 주입 고민 해 볼 것
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(REFRESH_KEY));
    }

    // 사용자 정보를 포함하는 jwt 생성
    // jwt 구조 : Header, Payload, Signature로 구성
    // Signature :  토큰의 검증을 위해 사용되며, Header, Payload를 암호화한 값을 생성
    public Token generateToken(Map<String, Object> claims) {

        Date now = new Date();
        Date accessExpiration = new Date(now.getTime() + ACCESS_TOKEN_EXP_TIME);
        Date refreshExpiration = new Date(now.getTime() + REFRESH_TOKEN_EXP_TIME);

        // accessToken 생성 ( 현재시간 + 1시간) :  HTTP 요청의 Authorization 헤더에 값으로 포함되어 전달
        String accessToken = Jwts.builder()
                .header().add(Map.of("typ", "JWT")).and() // 토큰형식 설정, 알고리즘 등 설정 가능
                .claims().empty().add(claims) // 사용자 정의 클레임 설정         // todo subject 없이 claims 안에 넣어서 옴 > 추후 변경 필요할지 고민해봐야 될 듯
                .issuedAt(now) // 토큰 발급 시간 설정 (claims 내부에 설정해야함)
                .expiration(accessExpiration) // 토큰 만료 시간 설정 (claims 내부에 설정해야함)
                .and()
                .signWith(secretKey) // 비밀키를 이용하여 서명 생성 (HMAC SHA256 사용)
                .compact();// 설정된 정보를 Base64로 인코딩된 JWT 문자열로 반환 (최종 JWT 생성)

        // refreshToken 토큰 생성
        String refreshToken = Jwts.builder()
                .expiration(refreshExpiration)
                .signWith(refreshKey)
                .compact();

        return Token.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Map<String, Object> validateAccessToken(String token) {
        return validateToken(token, secretKey);
    }

    public Map<String, Object> validateRefreshToken(String token) {
        return validateToken(token, refreshKey);
    }

    // jwt 검증
    public Map<String, Object> validateToken(String token, SecretKey key) {
        try {
            Map<String, Object> claims = Jwts.parser()
                    .verifyWith(key) // 지정 된 키로 검증
                    .build()
                    .parseSignedClaims(token) // 토큰 파싱 및 서명 검증
                    .getPayload(); // JWT 페이로드(Claims) 값을 가져옴 claims.get("exp")

            if (claims == null) {
                throw new CustomJWTException("Claim 파싱 실패 (null)");
            }

            return claims;

        } catch (ExpiredJwtException e) {
            throw new CustomJWTException("AccessToken 만료됨", e);
        } catch (MalformedJwtException e) {
            throw new CustomJWTException("JWT 형식 오류", e);
        } catch (InvalidClaimException e) {
            throw new CustomJWTException("JWT 클레임 오류", e);
        } catch (SecurityException e) {
            throw new CustomJWTException("JWT 서명 오류", e);
        } catch (UnsupportedJwtException e) {
            throw new CustomJWTException("지원하지 않는 JWT", e);
        } catch (JwtException e) {
            throw new CustomJWTException("JWT 처리 중 오류 발생", e);
        } catch (Exception e) {
            throw new CustomJWTException("알 수 없는 JWT 처리 오류", e);
        }
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Map<String, Object> claims = validateAccessToken(token);

        String email = (String) claims.get("email");
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // 비번 없이 인증 객체 생성
    }

    public boolean isTokenExpired(String token) {
        try {
            validateAccessToken(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            throw new CustomJWTException("토큰 검증 중 오류 발생", e);
        }
    }

    public boolean isExpiringSoon(Date expirationTime) {

        // 현재 시간과의 차이 계산 - 밀리세컨즈
        long timeLeftMillis = expirationTime.getTime() - System.currentTimeMillis();
        //분단위 계산
        long minutesLeft = timeLeftMillis / (1000 * 60);

        return minutesLeft < 60; // 1시간 미만
    }
}

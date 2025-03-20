package com.project.mallapi.security;

import com.project.mallapi.dto.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


@Component
public class JwtProvider {

    private static final String SECRET_KEY = "VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9XkHa";
    private static final String REFRESH_KEY = "V2pkQmZxS3NzTG5ZdXRXak5lZFlYSGIwc0xOZWZXVFE=";
    private static final long ACCESS_TOKEN_EXP_TIME = 1000 * 60 * 60; // 1시간
    private static final long REFRESH_TOKEN_EXP_TIME = 1000 * 60 * 60 * 24; // 24시간

    private final SecretKey secretKey;
    private final SecretKey refreshKey;

    public JwtProvider() {
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

    // jwt 검증 todo 추후 boolean으로 변경 예정, 필터에서 디버깅 위해 map으로 임시 설정
    public Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;
        try {
            claim = Jwts.parser()
                    .verifyWith(secretKey) // 지정 된 키로 검증
                    .build()
                    .parseSignedClaims(token) // 토큰 파싱 및 서명 검증
                    .getPayload(); // JWT 페이로드(Claims) 값을 가져옴
        } catch (MalformedJwtException malformedJwtException) {
//            throw new CustomJWTException("MalFormed");
        } catch (ExpiredJwtException expiredJwtException) {
//            throw new CustomJWTException("Expired");
        } catch (InvalidClaimException invalidClaimException) {
//            throw new CustomJWTException("Invalid");
        } catch (JwtException jwtException) {
//            throw new CustomJWTException("JWTError");
        } catch (Exception e) {
//            throw new CustomJWTException("Error");
        }
        return claim;
    }
}

package com.project.myapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.project.myapi.dto.LoginDto;
import com.project.myapi.util.AuthTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 스프링 부트 테스트를 위한 어노테이션
@AutoConfigureMockMvc //  MockMvc 인스턴스를 자동으로 구성
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;
    private String refreshToken;

    @BeforeEach
    @DisplayName("로그인 성공 테스트")
    void setUp() throws Exception {
        LoginDto loginDto = LoginDto.builder()
                .email("user1@aaa.com")
                .password("1111")
                .build();

        String loginRequest = objectMapper.writeValueAsString(loginDto);

        ResultActions loginResult = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk());

        String loginResponse = loginResult.andReturn().getResponse().getContentAsString();
        accessToken = JsonPath.read(loginResponse, "$.accessToken");
        refreshToken = JsonPath.read(loginResponse, "$.refreshToken");
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 비밀번호 오류")
    void loginFailWrongPassword() throws Exception {
        // given
        LoginDto loginDto = LoginDto.builder()
                .email("user1@aaa.com")
                .password("2222")
                .build();

        String content = objectMapper.writeValueAsString(loginDto);

        // when
        ResultActions result = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        result.andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    @DisplayName("Refresh 재발급 테스트 - AccessToken 유효 시 기존 토큰 유지")
    void refreshTokenTest_NoRenewalNeeded() throws Exception {
        // AccessToken이 아직 유효하므로 바로 요청
        ResultActions refreshResult = mockMvc.perform(post("/api/login/refresh")
                        .header("Authorization", "Bearer " + accessToken)
                        .content(refreshToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String refreshResponse = refreshResult.andReturn().getResponse().getContentAsString();
        String newAccessToken = JsonPath.read(refreshResponse, "$.accessToken");
        String newRefreshToken = JsonPath.read(refreshResponse, "$.refreshToken");

        assertEquals(accessToken, newAccessToken, "AccessToken이 유지되어야 합니다.");
        assertEquals(refreshToken, newRefreshToken, "RefreshToken도 유지되어야 합니다.");
    }

    @Test
    @DisplayName("Refresh 재발급 테스트 - RefreshToken 유효, Access Token은 만료 시 AccessToken만 재발급")
    void refreshTokenTest_OnlyAccessRenewed() throws Exception {

        Thread.sleep(11000); // 발급 시점만 다르게 만들기

        ResultActions refreshResult = mockMvc.perform(post("/api/login/refresh")
                        .header("Authorization", "Bearer " + accessToken)
                        .content(refreshToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String refreshResponse = refreshResult.andReturn().getResponse().getContentAsString();
        String newAccessToken = JsonPath.read(refreshResponse, "$.accessToken");
        String newRefreshToken = JsonPath.read(refreshResponse, "$.refreshToken");

        assertNotEquals(accessToken, newAccessToken, "AccessToken이 새로 발급되어야 합니다.");
        assertEquals(refreshToken, newRefreshToken, "RefreshToken은 기존 값이어야 합니다.");
    }

    @Test
    @DisplayName("Refresh 재발급 테스트 - RefreshToken이 만료 임박해서 둘 다 재발급")
    void refreshTokenTest_BothRenewed() throws Exception {

        Thread.sleep(11000); // accessToken 만료 + refreshToken 임박

        ResultActions refreshResult = mockMvc.perform(post("/api/login/refresh")
                        .header("Authorization", "Bearer " + accessToken)
                        .content(refreshToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String refreshResponse = refreshResult.andReturn().getResponse().getContentAsString();
        String newAccessToken = JsonPath.read(refreshResponse, "$.accessToken");
        String newRefreshToken = JsonPath.read(refreshResponse, "$.refreshToken");

        assertNotEquals(accessToken, newAccessToken, "AccessToken이 새로 발급되어야 합니다.");
        assertNotEquals(refreshToken, newRefreshToken, "RefreshToken도 새로 발급되어야 합니다.");
    }

    @Test
    @DisplayName("변조된 토큰으로 접근 시 인증 실패 테스트")
    void accessWithTamperedToken_shouldFail() throws Exception {

        // 토큰의 마지막 문자를 바꿔서 변조 (유효하지 않은 토큰)
        String tempToken = accessToken.substring(0, accessToken.length() - 1) + "x";

        mockMvc.perform(get("/api/hasRole")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tempToken))
                .andExpect(status().isUnauthorized()) // 401 Unauthorized
                .andDo(print());
    }


}

package com.project.myapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.project.myapi.dto.LoginDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() throws Exception {
        // given
        LoginDto loginDto = LoginDto.builder()
                .email("user1@aaa.com")
                .password("1111")
                .build();

        String content = objectMapper.writeValueAsString(loginDto);

        // when
        ResultActions result = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        result.andDo(print())
                .andExpect(status().isOk());

        String response = result.andReturn().getResponse().getContentAsString();
        String token = JsonPath.read(response, "$.accessToken");
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

}

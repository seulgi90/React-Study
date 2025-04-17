package com.project.myapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.myapi.util.AuthTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("권한 없는 요청 테스트")
    void roleAllAccess() throws Exception {
        // 로그인 요청
        String token = AuthTestUtil.getAccessToken(mockMvc, objectMapper, "user9@aaa.com", "1111");

        mockMvc.perform(post("/api/roleAll")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("ADMIN 권한이 있는 토큰으로 /api/hasRole 접근 성공")
    void accessWithAdminRole() throws Exception {
        // 로그인 요청
        String token = AuthTestUtil.getAccessToken(mockMvc, objectMapper, "user9@aaa.com", "1111");

        // 토큰으로 요청
        mockMvc.perform(get("/api/hasRole")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
package com.project.myapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.project.myapi.dto.LoginDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class AuthTestUtil {

    public static String getAccessToken(MockMvc mockMvc, ObjectMapper objectMapper, String email, String password) throws Exception {

        LoginDto loginDto = LoginDto.builder()
                .email(email)
                .password(password)
                .build();

        String json = objectMapper.writeValueAsString(loginDto);

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return JsonPath.read(response, "$.accessToken");
    }

}

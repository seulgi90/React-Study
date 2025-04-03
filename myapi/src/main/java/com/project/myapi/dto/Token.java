package com.project.myapi.dto;


import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
public class Token {

    private String grantType;

    private String accessToken;

    private String refreshToken;
}

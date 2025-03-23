package com.project.mallapi.dto;


import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
public class Token {

    private String grantType;

    private String accessToken;

    private String refreshToken;
}

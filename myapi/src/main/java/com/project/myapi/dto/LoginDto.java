package com.project.myapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDto {

    private String email;

    private String password;
}

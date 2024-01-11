package com.hounter.backend.application.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String avatar;
    private String accessToken;
}

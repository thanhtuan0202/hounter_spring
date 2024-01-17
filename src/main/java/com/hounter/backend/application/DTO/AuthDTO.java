package com.hounter.backend.application.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private List<String> roles;
    private String accessToken;
}

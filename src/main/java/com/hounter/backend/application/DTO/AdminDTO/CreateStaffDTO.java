package com.hounter.backend.application.DTO.AdminDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStaffDTO {
    private String username;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;
}

package com.hounter.backend.application.DTO.AdminDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerListResDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String address;
    private LocalDate createAt;
    private String isActive;
}

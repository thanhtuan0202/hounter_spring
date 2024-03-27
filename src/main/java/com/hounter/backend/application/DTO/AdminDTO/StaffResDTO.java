package com.hounter.backend.application.DTO.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffResDTO {
    private Long id;
    private String fullName;
    private String username;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDate startDate;
    private String isActive;
}

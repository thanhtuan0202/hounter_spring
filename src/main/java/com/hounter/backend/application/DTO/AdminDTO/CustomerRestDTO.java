package com.hounter.backend.application.DTO.AdminDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerRestDTO {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private Boolean isActive;
    private LocalDate createAt;
    private Integer numOfPost;
    private Integer totalPayments;
}

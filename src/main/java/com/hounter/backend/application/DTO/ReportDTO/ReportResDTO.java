package com.hounter.backend.application.DTO.ReportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResDTO {
    private String title;
    private String description;
    private LocalDate createDate;
    private LocalDate closeDate;
    private Boolean isResolved;
    private String customerName;
    private String customerPhoneNumber;
    private String staffName;
}

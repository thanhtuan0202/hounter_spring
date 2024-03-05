package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.ReportDTO.ReportResDTO;
import com.hounter.backend.business_logic.entities.Report;

public class ReportMapping {
    public static ReportResDTO mapToReportResDTO(Report report) {
        return new ReportResDTO();
    }
}

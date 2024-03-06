package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;
import com.hounter.backend.application.DTO.ReportDTO.ReportResDTO;

import java.util.List;

public interface StaffService {
    StaffResDTO getStaffInfoById(Long staffId);
    List<ReportResDTO> getStaffReports(Long staffId, String fromDate, String toDate, Boolean isResolved, Integer pageSize, Integer pageNo);
    ReportResDTO getStaffReportById(Long staffId, Long reportId);
}

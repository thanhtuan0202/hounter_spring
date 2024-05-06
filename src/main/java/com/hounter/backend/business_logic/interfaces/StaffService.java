package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;
import com.hounter.backend.application.DTO.ReportDTO.ReportResDTO;
import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.Staff;

import java.util.List;

public interface StaffService {
    StaffResDTO getStaffInfoById(Long staffId);
    List<ReportResDTO> getStaffReports(Long staffId, String fromDate, String toDate, Boolean isResolved, Integer pageSize, Integer pageNo);
    ReportResDTO getStaffReportById(Long staffId, Long reportId);
    void requestDeleteAccount(Account staff);
    void requestDeletePost(Post post);
    Staff getStaffById(Long staffId);
}

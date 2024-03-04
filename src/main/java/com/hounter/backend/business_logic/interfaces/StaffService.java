package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;

public interface StaffService {
    StaffResDTO getStaffInfoById(Long staffId);
}

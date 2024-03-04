package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;

public interface StaffService {
    public StaffResDTO getStaffInfoById(Long staffId);
}

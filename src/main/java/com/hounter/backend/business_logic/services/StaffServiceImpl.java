package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;
import com.hounter.backend.business_logic.entities.Staff;
import com.hounter.backend.business_logic.interfaces.StaffService;
import com.hounter.backend.data_access.repositories.StaffRepository;
import com.hounter.backend.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public StaffResDTO getStaffInfoById(Long staffId) {
        Optional<Staff> optionalStaff = this.staffRepository.findById(staffId);
        if(optionalStaff.isEmpty()){
            throw new NotFoundException("Staff not found.", HttpStatus.OK);
        }
        Staff staff = optionalStaff.get();
        return new StaffResDTO(staff.getId(), staff.getFull_name(), staff.getUsername(),
                staff.getPhoneNumber(), staff.getEmail(), staff.getAddress(), staff.getStartDate(), staff.getIsActive());
    }
}

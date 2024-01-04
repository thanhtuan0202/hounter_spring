package com.hounter.backend.business_logic.services;

import com.hounter.backend.business_logic.interfaces.StaffService;
import com.hounter.backend.data_access.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

}

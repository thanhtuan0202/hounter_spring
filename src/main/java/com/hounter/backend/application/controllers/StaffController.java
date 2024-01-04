package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.business_logic.interfaces.StaffService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staffs")
public class StaffController {
    @Autowired
    private StaffService staffService;

    private final CustomUserDetailServiceImpl userDetailsService;

    public StaffController(CustomUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public ResponseEntity<?> getListStaff(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "sortBy", defaultValue = "createAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ){
        return null;
    }
    @GetMapping("/{staffId}")
    public ResponseEntity<?> getStaffInfo(@PathVariable("staffId") Long staffId) {
        return null;
    }

    @GetMapping("/{staffId}/reports")
    public ResponseEntity<?> getStaffReports(@PathVariable("staffId") Long staffId){
        return null;
    }

    @GetMapping("/{staffId}/reports/{reportId}")
    public ResponseEntity<?> getDetailReport(@PathVariable("staffId") Long staffId, @PathVariable("reportId") Long reportId){
        return null;
    }

    @PostMapping()
    public ResponseEntity<?> createStaff(){
        return null;
    }

    @PutMapping("/{staffId}")
    public ResponseEntity<?> updateStaff(@PathVariable("staffId") Long staffId){
        return null;
    }

    @DeleteMapping("/{staffId}")
    public ResponseEntity<?> deleteStaff(@PathVariable("staffId") Long staffId){
        return null;
    }
}

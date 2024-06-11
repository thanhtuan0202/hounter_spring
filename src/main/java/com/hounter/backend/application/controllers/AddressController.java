package com.hounter.backend.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hounter.backend.business_logic.services.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping()
    public ResponseEntity<?> getListProvince() {
        return ResponseEntity.ok(this.addressService.getProvinceList());
    }
    
    @GetMapping("/province/{id}")
    public ResponseEntity<?> getDetailProvince(@PathVariable("id") Long provinceId){
        return ResponseEntity.ok(this.addressService.getDetailProvince(provinceId.intValue()));
    }

    @GetMapping("/find-ward")
    public ResponseEntity<?> findWardId(@RequestParam("ward") String ward,
                                        @RequestParam("district") String district,
                                        @RequestParam("province") String province){
        return ResponseEntity.ok(this.addressService.findWardId(ward, district, province));
    }
}

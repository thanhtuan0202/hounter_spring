package com.hounter.backend.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hounter.backend.business_logic.services.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}

package com.hounter.backend.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hounter.backend.application.DTO.PlaceDTO;
import com.hounter.backend.business_logic.interfaces.PlaceService;
import com.hounter.backend.shared.enums.PlaceType;
import com.hounter.backend.shared.utils.GoongUtils;

@RestController
@RequestMapping("/place")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @Autowired
    private GoongUtils goongUtils;

    @GetMapping()
    public ResponseEntity<?> getPlaces(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "wardId", defaultValue = "1") Integer ward,
            @RequestParam(value =  "type") PlaceType type
    ){
        try{
            List<PlaceDTO> response = this.placeService.getAllPlaces(pageSize, pageNo - 1, ward, type);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> addPlace(@RequestBody PlaceDTO placeDTO) {
        try {
            PlaceDTO response = this.placeService.addPlace(placeDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-point")
    public ResponseEntity<?> getPoint(
            @RequestParam(value = "address") String address
    ){
        try{
            return ResponseEntity.ok(goongUtils.getAddressLngLat(address));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

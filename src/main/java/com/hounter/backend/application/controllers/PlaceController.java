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
import com.hounter.backend.shared.utils.FindPointMapbox;

@RestController
@RequestMapping("/place")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    private final FindPointMapbox findPointMapbox;

    public PlaceController(FindPointMapbox findPointMapbox) {
        // this.findPointsAddress = findPointsAddress;
        this.findPointMapbox = findPointMapbox;
    }

    @GetMapping()
    public ResponseEntity<?> getPlaces(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "district", defaultValue = "") String district,
            @RequestParam(value =  "type") PlaceType type
    ){
        try{
            List<PlaceDTO> response = this.placeService.getAllPlaces(pageSize, pageNo - 1, district, type);
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
            return ResponseEntity.ok(findPointMapbox.getAddressPoints(address));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

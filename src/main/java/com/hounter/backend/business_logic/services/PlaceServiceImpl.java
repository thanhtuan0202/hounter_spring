package com.hounter.backend.business_logic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hounter.backend.business_logic.interfaces.PlaceService;
import com.hounter.backend.data_access.repositories.AddressRepository;
import com.hounter.backend.data_access.repositories.PlaceRepository;
import com.hounter.backend.data_access.repositories.WardRepository;
import com.hounter.backend.shared.enums.PlaceType;
import com.hounter.backend.application.DTO.PlaceDTO;
import com.hounter.backend.business_logic.entities.Place;
import com.hounter.backend.business_logic.entities.Ward;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Service
public class PlaceServiceImpl implements PlaceService{
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private WardRepository wardRepository;

    @Override
    public List<PlaceDTO> getAllPlaces(Integer pageSize, Integer pageNo, Integer wardId, PlaceType type) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Optional<Ward> wardOp = this.wardRepository.findByCode(wardId);
        if(wardOp.isEmpty()){
            return null;
        }
        Ward ward = wardOp.get();
        List<Place> places;
        if (type == null) {
            places = placeRepository.findByWard(ward, pageable);
        }
        else {
            places = placeRepository.findByTypeAndWard(ward, type, pageable);
        }
        List<PlaceDTO> placeDTOs = new ArrayList<>();
        for (Place place : places) {
            placeDTOs.add(new PlaceDTO(place));
        }
        return placeDTOs;
    }

    @Override
    public PlaceDTO addPlace(PlaceDTO placeDTO) throws Exception {
        Place place = new Place();
        place.setType(placeDTO.getType());
        place.setName(placeDTO.getName());
        place.setAddress(placeDTO.getAddress());
        place.setLatitude(placeDTO.getLatitude());
        place.setLongitude(placeDTO.getLongitude());
        List <String> districts = new ArrayList<>();
        districts =  Arrays.asList(placeDTO.address.split(", "));
        String ward = districts.get(districts.size() - 3);
        List <String> temp = Arrays.asList(ward.split(" "));
        if (temp.get(temp.size() -1).length() == 1) {
            ward = temp.get(0) + " 0" + temp.get(1);
        }
        String district = districts.get(districts.size() - 2);
        Ward optionalWard = this.wardRepository.searchByDistrictAndWard(district, ward);
        if(optionalWard == null){
            return new PlaceDTO();
        }
        place.setWard(optionalWard);
        placeRepository.save(place);
        return new PlaceDTO(place);
    }
}

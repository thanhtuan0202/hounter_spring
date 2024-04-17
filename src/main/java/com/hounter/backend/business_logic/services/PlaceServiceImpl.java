package com.hounter.backend.business_logic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hounter.backend.business_logic.interfaces.PlaceService;
import com.hounter.backend.data_access.repositories.PlaceRepository;
import com.hounter.backend.shared.enums.PlaceType;
import com.hounter.backend.application.DTO.PlaceDTO;
import com.hounter.backend.business_logic.entities.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Service
public class PlaceServiceImpl implements PlaceService{
    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public List<PlaceDTO> getAllPlaces(Integer pageSize, Integer pageNo, String district, PlaceType type) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Place> places;
        if (type == null) {
            places = placeRepository.findByDistrict(district, pageable);
        }
        else {
            places = placeRepository.findByTypeAndDistrict(district, type, pageable);
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
        districts =  Arrays.asList(placeDTO.address.split(","));
        place.setDistrict(districts.get(districts.size() - 2));
        placeRepository.save(place);
        return new PlaceDTO(place);
    }
}

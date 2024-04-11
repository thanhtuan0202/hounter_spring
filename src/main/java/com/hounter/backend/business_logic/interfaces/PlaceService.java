package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.PlaceDTO;
import java.util.List;

public interface PlaceService {
    List<PlaceDTO> getAllPlaces(Integer pageSize, Integer pageNo, String district);
    PlaceDTO addPlace(PlaceDTO placeDTO) throws Exception;
}

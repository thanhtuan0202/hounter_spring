package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.FavoriteDto.FavoriteResponse;
import com.hounter.backend.business_logic.entities.FavoritePost;

public class FavoriteMapping {
    public static FavoriteResponse responseMapping(FavoritePost post){
        FavoriteResponse response = new FavoriteResponse();
        
        return response;
    }
}

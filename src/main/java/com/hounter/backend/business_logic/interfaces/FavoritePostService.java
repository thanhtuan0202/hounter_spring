package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.FavoriteDto.FavoriteResponse;

import java.util.List;

public interface FavoritePostService {
    public List<FavoriteResponse> getAllFavoritePost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,Long userId);
    public boolean addPostToFavorite(Long userId, Long postId) throws Exception;
    public boolean deletePostFromFavorite(Long userId, Long postId) throws Exception;

}

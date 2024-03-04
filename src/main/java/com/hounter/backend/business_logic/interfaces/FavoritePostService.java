package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.FavoriteDto.FavoriteResponse;

import java.util.List;

public interface FavoritePostService {
    List<FavoriteResponse> getAllFavoritePost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,Long userId);
    boolean addPostToFavorite(Long userId, Long postId) throws Exception;
    boolean deletePostFromFavorite(Long userId, Long postId) throws Exception;

}

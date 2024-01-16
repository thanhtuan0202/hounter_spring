package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.FavoriteDto.FavoriteResponse;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;

import java.util.List;

public interface FavoritePostService {
    public List<ShortPostResponse> getAllFavoritePost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,Long userId);
    public boolean addPostToFavorite(Long userId, Long postId) throws Exception;
    public boolean deletePostFromFavorite(Long userId, Long postId) throws Exception;

}

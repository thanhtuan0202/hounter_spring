package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.FavoritePostDTO;

import java.util.Set;

public interface FavoritePostService {
    public Set<FavoritePostDTO.FavoritePostResponse> getAllFavoritePost(Long userId);
    public FavoritePostDTO.FavoritePostResponse addPostToFavorite(Long userId, Long postId);
    public FavoritePostDTO.FavoritePostResponse deletePostFromFavorite(Long userId, Long postId);

    Object getFavoritePost(Long userId);
}

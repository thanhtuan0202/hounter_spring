package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.FavoritePostDTO;
import com.hounter.backend.business_logic.interfaces.FavoritePostService;
import com.hounter.backend.data_access.repositories.FavoritePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FavoritePostServiceImpl implements FavoritePostService {
    @Autowired
    private FavoritePostRepository favoritePostRepository;

    @Override
    public Set<FavoritePostDTO.FavoritePostResponse> getAllFavoritePost(Long userId) {
        return null;
    }

    @Override
    public FavoritePostDTO.FavoritePostResponse addPostToFavorite(Long userId, Long postId) {
        return null;
    }

    @Override
    public FavoritePostDTO.FavoritePostResponse deletePostFromFavorite(Long userId, Long postId) {
        return null;
    }

    @Override
    public Object getFavoritePost(Long userId) {
        return null;
    }
}

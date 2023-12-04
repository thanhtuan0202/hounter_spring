package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.FavoritePostDTO;
import com.hounter.backend.business_logic.interfaces.FavoritePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favorite-posts")
public class FavoritePostController {
    @Autowired
    private FavoritePostService favoritePostService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getFavoritePost(@PathVariable("userId") Long userId){
        Set<FavoritePostDTO.FavoritePostResponse> postList = (Set<FavoritePostDTO.FavoritePostResponse>) favoritePostService.getFavoritePost(userId);
        return null;
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<?> addNewPostToFavorite(@PathVariable("userId") Long userId,@PathVariable("postId") Long postId){
        return null;
    }
    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<?> deletePostFromFavorite(@PathVariable("userId") Long userId,@PathVariable("postId") Long postId){
        return null;
    }
}

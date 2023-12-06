package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.FavoriteDto.FavoriteResponse;
import com.hounter.backend.business_logic.interfaces.FavoritePostService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.exceptions.PostNotFoundException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite-posts")
public class FavoritePostController {
    private final CustomUserDetailServiceImpl customUserDetailService;
    @Autowired
    private FavoritePostService favoritePostService;

    public FavoritePostController(CustomUserDetailServiceImpl customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @GetMapping()
    public ResponseEntity<?> getFavoritePost(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {
        try {
            Long userId = this.customUserDetailService.getCurrentUserDetails().getUserId();
            List<FavoriteResponse> responses = this.favoritePostService.getAllFavoritePost(pageSize, pageNo, "createAt",
                    "desc", userId);
            if (responses != null) {
                return ResponseEntity.ok(responses);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<String>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> addNewPostToFavorite(@Valid @PathVariable("postId") Long postId) {
        try {
            Long userId = this.customUserDetailService.getCurrentUserDetails().getUserId();
            this.favoritePostService.addPostToFavorite(userId, postId);
            return ResponseEntity.ok("Thêm thành công!");
        } catch (PostNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePostFromFavorite(@PathVariable("postId") Long postId) {
        try {
            Long userId = this.customUserDetailService.getCurrentUserDetails().getUserId();
            this.favoritePostService.addPostToFavorite(userId, postId);
            return ResponseEntity.ok("Thêm thành công!");
        } catch (PostNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>("Can't find post in your favorite list", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<String>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

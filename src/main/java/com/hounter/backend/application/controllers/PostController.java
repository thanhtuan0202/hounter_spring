package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.PostDto.CreatePostDto;
import com.hounter.backend.application.DTO.PostDto.FilterPostDto;
import com.hounter.backend.application.DTO.PostDto.PostResponse;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.interfaces.PostService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.enums.Status;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    private final CustomUserDetailServiceImpl userDetailsService;

    public PostController(CustomUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllPosts(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "sortBy", defaultValue = "createAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        try {
            List<ShortPostResponse> response = this.postService.getAllPost(pageSize, pageNo, sortBy, sortDir,Status.active);
            if (response == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable("id") Long id){
        try {
            PostResponse post = this.postService.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/filter")
    public ResponseEntity<?> getFilterPosts(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "sortBy", defaultValue = "createAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok("go to filter controller");
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchPosts(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "sortBy", defaultValue = "createAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "q") String searchValue) {
        return ResponseEntity.ok("go to search controller" + searchValue );
    }

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDto createPost, BindingResult bindingResult){
        Long user_id = this.userDetailsService.getCurrentUserDetails().getUserId();
        try {
            PostResponse response = this.postService.createPost(createPost, user_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@RequestBody CreatePostDto updatePost, @PathVariable("id") Long postId) {
        Long user_id = this.userDetailsService.getCurrentUserDetails().getUserId();
        try {
            PostResponse response = this.postService.updatePost(postId, updatePost, user_id);

        } catch (Exception e) {
            
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long postId) {
        boolean result = this.postService.deletePost(postId, 1L);
        return null;
    }

}

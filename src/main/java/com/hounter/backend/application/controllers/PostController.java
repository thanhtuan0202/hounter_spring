package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.ApiResponse.ApiResponse;
import com.hounter.backend.application.DTO.PostDto.ChangeStatusDto;
import com.hounter.backend.application.DTO.PostDto.CreatePostDto;
import com.hounter.backend.application.DTO.PostDto.FilterPostDto;
import com.hounter.backend.application.DTO.PostDto.PostResponse;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.interfaces.PostService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.binding.BindingBadRequest;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.ForbiddenException;
import com.hounter.backend.shared.utils.MappingError;

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
            List<ShortPostResponse> response = this.postService.getAllPost(pageSize, pageNo, sortBy, sortDir,
                    Status.active);
            if (response == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable("id") Long id) {
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
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @Valid @ModelAttribute FilterPostDto filterDto) {
        return ResponseEntity.ok("go to filter controller");
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchPosts(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "sortBy", defaultValue = "createAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "q") String searchValue) {
        return ResponseEntity.ok("go to search controller" + searchValue);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDto createPost, BindingResult binding) {
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = MappingError.mappingError(binding);
            return ResponseEntity.badRequest().body(error_lst);
        }
        try {
            Long user_id = this.userDetailsService.getCurrentUserDetails().getUserId();
            PostResponse response = this.postService.createPost(createPost, user_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody CreatePostDto updatePost,
            @Valid @PathVariable("id") Long postId,
            BindingResult binding) {
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = MappingError.mappingError(binding);
            return ResponseEntity.badRequest().body(error_lst);
        }
        try {
            Long user_id = this.userDetailsService.getCurrentUserDetails().getUserId();
            PostResponse response = this.postService.updatePost(postId, updatePost, user_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changePostStatus(@Valid @PathVariable("id") Long postId,
            @Valid @RequestBody ChangeStatusDto changeStatus, BindingResult binding) {
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = MappingError.mappingError(binding);
            return ResponseEntity.badRequest().body(error_lst);
        }
        try {
            Long userId = this.userDetailsService.getCurrentUserDetails().getUserId();
            boolean updatePost = this.postService.changeStatusPost(postId, userId, changeStatus, false);
            if (updatePost) {
                return ResponseEntity.ok("Thay đổi thành công");
            } else {
                return ResponseEntity.ok(new ApiResponse<String>("Can't update post status after delete!"));
            }
        } catch (ForbiddenException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long postId) {
        boolean result = this.postService.deletePost(postId, 1L);
        return null;
    }

}

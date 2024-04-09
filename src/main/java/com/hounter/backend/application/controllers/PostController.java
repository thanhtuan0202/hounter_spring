package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.ApiResponse.ApiResponse;
import com.hounter.backend.application.DTO.FeedbackDto.CreateFeedback;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;
import com.hounter.backend.application.DTO.PostDto.*;
import com.hounter.backend.business_logic.interfaces.PostService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.binding.BindingBadRequest;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.ForbiddenException;
import com.hounter.backend.shared.exceptions.PostNotFoundException;
import com.hounter.backend.shared.utils.FindPointMapbox;
import com.hounter.backend.shared.utils.FindPointsAddress;
import com.hounter.backend.shared.utils.MappingError;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/posts")
@Log4j2
public class PostController {
    @Autowired
    private PostService postService;

    private final CustomUserDetailServiceImpl userDetailsService;
    // private final FindPointsAddress findPointsAddress;
    private final FindPointMapbox findPointMapbox;
    public PostController(CustomUserDetailServiceImpl userDetailsService, FindPointsAddress findPointsAddress, FindPointMapbox findPointMapbox) {
        this.userDetailsService = userDetailsService;
        // this.findPointsAddress = findPointsAddress;
        this.findPointMapbox = findPointMapbox;
    }

    @GetMapping()
    public ResponseEntity<?> getAllPosts(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "type", defaultValue = "1") Long type ) {
        try {
            List<ShortPostResponse> response = this.postService.getAllPost(pageSize, pageNo - 1, "createAt", "desc",
                    Status.active);
            if (response == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable("id") Long id) {
        try {
            PostResponse post = this.postService.getPostById(id, true);
            if(post == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/feedbacks")
    public ResponseEntity<?> getPostFeedback(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @PathVariable("id") Long id) {
        try {
            List<FeedbackResponse> response = this.postService.getPostFeedback(pageSize, pageNo - 1,id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/filter")
    public ResponseEntity<?> getFilterPosts(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @Valid @ModelAttribute FilterPostDto filterDto, BindingResult binding) {
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = MappingError.mappingError(binding);
            log.error("Error mapping request " + binding);
            return ResponseEntity.badRequest().body(error_lst);
        }
        try{
            List<ShortPostResponse> responses = this.postService.filterPost(pageSize, pageNo - 1, "createAt", "desc", filterDto);
            if (responses == null) {
                return ResponseEntity.noContent().build();
            }
            log.info("Filter post response returned " + responses.size());
            return ResponseEntity.ok(responses);
        }
        catch(Exception e){
            log.error("Error: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchPosts(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "sortBy", defaultValue = "createAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(value = "q") String searchValue) {
        return ResponseEntity.ok("go to search controller" + searchValue);
    }

    @GetMapping("/search-on-map")
    public ResponseEntity<?> SearchOnMap(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "latitude") float latitude,
            @RequestParam(value = "longitude") float longitude
    ){
        try {
            List<ShortPostResponse> responses = this.postService.searchOnMap(latitude, longitude, pageSize, pageNo - 1);
            return ResponseEntity.ok(responses);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/feedback")
    public ResponseEntity<?> createNewFeedback(@Valid @PathVariable("id") Long postId, @Valid @RequestBody CreateFeedback createFeedback, BindingResult binding){
        if(binding.hasErrors()){
            List<BindingBadRequest> errors = MappingError.mappingError(binding);
            return ResponseEntity.badRequest().body(errors);
        }
        try{
            Long customerId = this.userDetailsService.getCurrentUserDetails().getUserId();
            FeedbackResponse response = this.postService.createNewFeedback(createFeedback,postId,customerId);
            return ResponseEntity.ok(response);
        }
        catch (PostNotFoundException e){
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody UpdatePostDTO updatePost,
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
                return ResponseEntity.ok(new ApiResponse<>("Can't update post status after delete!"));
            }
        } catch (ForbiddenException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@Valid @PathVariable("id") Long postId) {
        try{
            Long userId = this.userDetailsService.getCurrentUserDetails().getUserId();
            PostResponse postResponse = this.postService.deletePost(postId, userId);
            return ResponseEntity.ok(postResponse);
        }
        catch (PostNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
        catch (ForbiddenException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/find_address")
    public ResponseEntity<?> findAddress(@RequestBody String address) throws IOException {
//        return ResponseEntity.ok(findPointsAddress.getAddressPoints(address));
        return ResponseEntity.ok(findPointMapbox.getAddressPoints(address));
    }

    @GetMapping("/find_post")
    public ResponseEntity<?> findPost(@Valid @ModelAttribute FindPostDTO findPostDTO, BindingResult binding) {
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = MappingError.mappingError(binding);
            return ResponseEntity.badRequest().body(error_lst);
        }
        try {
            List<SummaryPostDTO> response = this.postService.findAvancedPost(findPostDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

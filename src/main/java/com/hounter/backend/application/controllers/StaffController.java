package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.PostDto.ChangeStatusDto;
import com.hounter.backend.application.DTO.PostDto.PostResponse;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.interfaces.PostService;
import com.hounter.backend.business_logic.interfaces.StaffService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.enums.Status;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staffs")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private PostService postService;

    private final CustomUserDetailServiceImpl userDetailsService;

    public StaffController(CustomUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/get-self-infomation")
    public ResponseEntity<?> getStaffInfo() {
        try {
            Long staffId = this.userDetailsService.getCurrentUserDetails().getUserId();
            return ResponseEntity.ok(this.staffService.getStaffInfoById(staffId));
        } catch (Exception e) {
            return new ResponseEntity<String>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<?> staffGetPost(
        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(value = "type", defaultValue = "1") Long type,
        @RequestParam(value = "status", defaultValue = "waiting") Status status
    ){
        try {
            List<ShortPostResponse> response = this.postService.getAllPost(pageSize, pageNo - 1, "createAt", "desc",
                    status);
            if (response == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable("id") Long id) {
        try {
            PostResponse post = this.postService.getPostById(id, false);
            if(post == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{staffId}/reports")
    public ResponseEntity<?> getStaffReports(@PathVariable("staffId") Long staffId){
        return null;
    }

    @GetMapping("/{staffId}/reports/{reportId}")
    public ResponseEntity<?> getDetailReport(@PathVariable("staffId") Long staffId, @PathVariable("reportId") Long reportId){
        return null;
    }

    @PostMapping()
    public ResponseEntity<?> createStaff(){
        return null;
    }

    @PutMapping("/{staffId}")
    public ResponseEntity<?> updateStaff(@PathVariable("staffId") Long staffId){
        return null;
    }
    @PatchMapping("/posts/{id}")
    public ResponseEntity<?> changeStatusPost(@Valid @PathVariable("id") Long postId,@Valid @RequestBody ChangeStatusDto status) {
        Long staffId = this.userDetailsService.getCurrentUserDetails().getUserId();
        try {
            boolean updatePost = this.postService.changeStatus_Staff(postId,  status.getStatus(), staffId);
            if (updatePost) {
                return ResponseEntity.ok("Thay đổi thành công");
            } else {
                return new ResponseEntity<>("Can't update post status!", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

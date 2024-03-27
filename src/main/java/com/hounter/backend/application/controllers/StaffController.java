package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.PostDto.ChangeStatusDto;
import com.hounter.backend.application.DTO.PostDto.PostResponse;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.interfaces.PostService;
import com.hounter.backend.business_logic.interfaces.StaffService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
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

    @GetMapping("/get-self-information")
    public ResponseEntity<?> getStaffInfo() {
        try {
            Long staffId = this.userDetailsService.getCurrentUserDetails().getUserId();
            return ResponseEntity.ok(this.staffService.getStaffInfoById(staffId));
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<?> staffGetPost(
        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(value = "category", defaultValue = "") String category,
        @RequestParam(value = "cost", defaultValue = "") String cost,
        @RequestParam(value = "status", defaultValue = "") Status status,
        @RequestParam(value = "fromDate", defaultValue = "") String fromDate,
        @RequestParam(value = "toDate", defaultValue = "") String toDate
    ){
        try {
            List<ShortPostResponse> response = this.postService.filterPostForUser(pageSize, pageNo - 1, null, category, cost, fromDate, toDate, status);
            if (response == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> getStaffReports(@PathVariable("staffId") Long staffId,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                             @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(value = "isResolved", required = false) Boolean isResolved,
                                             @RequestParam(value = "fromDate", required = false) String fromDate,
                                             @RequestParam(value = "toDate", required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "From date must be in the format yyyy-MM-dd") String toDate
    ){
        try{
            Long currentStaffId = this.userDetailsService.getCurrentUserDetails().getUserId();
            if(!currentStaffId.equals(staffId)){
                return new ResponseEntity<>("You don't have permission to access this report", HttpStatus.FORBIDDEN);
            }
            return ResponseEntity.ok(this.staffService.getStaffReports(staffId, fromDate, toDate, isResolved, pageSize, pageNo));
        }
        catch(DateTimeParseException e){
            return new ResponseEntity<>("Invalid date format. Date must be in the format YYYY-MM-DD", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{staffId}/reports/{reportId}")
    public ResponseEntity<?> getDetailReport(@PathVariable("staffId") Long staffId, @PathVariable("reportId") Long reportId){
        try{
            Long currentStaffId = this.userDetailsService.getCurrentUserDetails().getUserId();
            if(!currentStaffId.equals(staffId)){
                return new ResponseEntity<>("You don't have permission to access this report", HttpStatus.FORBIDDEN);
            }
            return ResponseEntity.ok(this.staffService.getStaffReportById(staffId, reportId));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping("/{staffId}")
//    public ResponseEntity<?> updateStaff(@PathVariable("staffId") Long staffId){
//        return null;
//    }
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

package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.ApiResponse.ApiResponse;
import com.hounter.backend.application.DTO.FeedbackDto.CreateFeedback;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;
import com.hounter.backend.business_logic.interfaces.FeedbackService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.binding.BindingBadRequest;
import com.hounter.backend.shared.exceptions.PostNotFoundException;
import com.hounter.backend.shared.utils.MappingError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    private final CustomUserDetailServiceImpl userDetailsService;

    public FeedbackController(CustomUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @GetMapping
    public ResponseEntity<?> getAllFeedback(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {
        try {
            List<FeedbackResponse> responses = this.feedbackService.getAllFeedback(pageSize, pageNo - 1, "createAt", "desc");
            if (responses != null) {
                return ResponseEntity.ok(responses);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFeedbackById(@Valid @PathVariable("id") Long id) {
        try{
            FeedbackResponse response = this.feedbackService.getFeedbackById(id);
            if (response != null) {
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedbackById(@Valid @PathVariable("id") Long feedbackId){
        return null;
    }
    @PostMapping("/{postId}")
    public ResponseEntity<?> createFeedback(@Valid @PathVariable("postId") Long postId,@Valid @RequestBody CreateFeedback createFeedback, BindingResult binding){
        if(binding.hasErrors()){
            List<BindingBadRequest> errors = MappingError.mappingError(binding);
            return ResponseEntity.badRequest().body(errors);
        }
        try{
            Long customerId = this.userDetailsService.getCurrentUserDetails().getUserId();
            FeedbackResponse response = this.feedbackService.createFeedback(createFeedback,postId,customerId);
            return ResponseEntity.ok(response);
        }
        catch (PostNotFoundException e){
            return new ResponseEntity<>(new ApiResponse<String>(e.getMessage()), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

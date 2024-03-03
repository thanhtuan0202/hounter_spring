package com.hounter.backend.business_logic.interfaces;

import java.util.List;

import com.hounter.backend.application.DTO.FeedbackDto.CreateFeedback;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;
public interface FeedbackService {
    public List<FeedbackResponse> getAllFeedback(Integer pageSize, Integer pageNo, String sortBy, String sortDir);
    public List<FeedbackResponse> getFeedbackByPost(Long postId, Integer pageSize, Integer pageNo, String sortBy, String sortDir);
    public FeedbackResponse getFeedbackById(Long id);
    public FeedbackResponse createFeedback(CreateFeedback createFeedback, Long postId, Long userId);
}   

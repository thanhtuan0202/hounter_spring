package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.FeedbackDto.CreateFeedback;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;

import java.util.List;
public interface FeedbackService {
    List<FeedbackResponse> getAllFeedback(Integer pageSize, Integer pageNo, String sortBy, String sortDir);
    List<FeedbackResponse> getFeedbackByPost(Long postId, Integer pageSize, Integer pageNo, String sortBy, String sortDir);
    FeedbackResponse getFeedbackById(Long id);
    FeedbackResponse createFeedback(CreateFeedback createFeedback, Long postId, Long userId);
}

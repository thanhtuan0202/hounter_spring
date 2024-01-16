package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;
import com.hounter.backend.business_logic.entities.Feedback;

public class FeedbackMapping {
    public static FeedbackResponse getResponse(Feedback feedback){
        FeedbackResponse response = new FeedbackResponse();
        response.setId(feedback.getId());
        response.setContent(feedback.getContent());
        response.setCreate_at(feedback.getCreateAt());
        response.setPost(feedback.getPost().getId());
        response.setSender(feedback.getCustomer().getId());
        return response;
    }
}

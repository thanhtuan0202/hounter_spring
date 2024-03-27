package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.FeedbackDto.FeedbackDetailResponse;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;
import com.hounter.backend.application.DTO.PostDto.ShortCustomer;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Feedback;

public class FeedbackMapping {
    public static FeedbackResponse getResponse(Feedback feedback){
        FeedbackResponse response = new FeedbackResponse();
        response.setId(feedback.getId());
        response.setContent(feedback.getContent());
        response.setCreateAt(feedback.getCreateAt());
        response.setPostId(feedback.getPost().getId());
        response.setPostTitle(feedback.getPost().getTitle());
        Customer customer = feedback.getCustomer();
        response.setSender(new ShortCustomer(customer.getId(), customer.getFull_name(), customer.getAvatar()));
        return response;
    }

    public static FeedbackDetailResponse getDetailResponse(Feedback feedback){
        FeedbackDetailResponse response = new FeedbackDetailResponse();
        response.setId(feedback.getId());
        response.setContent(feedback.getContent());
        response.setCreateAt(feedback.getCreateAt());
        response.setPostId(feedback.getPost().getId());
        response.setPostTitle(feedback.getPost().getTitle());
        response.setPostDescription(feedback.getPost().getDescription());
        Customer customer = feedback.getCustomer();
        response.setSender(new ShortCustomer(customer.getId(), customer.getFull_name(), customer.getAvatar()));
        Customer postOwner = feedback.getPost().getCustomer();
        response.setPostOwner(new ShortCustomer(postOwner.getId(), postOwner.getFull_name(), postOwner.getAvatar()));
        return response;
    }
}

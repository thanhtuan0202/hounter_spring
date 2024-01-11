package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.FeedbackDto.CreateFeedback;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;
import com.hounter.backend.application.DTO.PostDto.*;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.shared.enums.Status;

import java.util.List;

public interface PostService {
    //public to all users
    public PostResponse getPostById(Long postId);
    public List<ShortPostResponse> getAllPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir);
    public List<ShortPostResponse> getAllPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir, Status status);
    //user
    public List<ShortPostResponse> getAllPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir, Customer customer);
    public List<ShortPostResponse> getAllPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir, Customer customer, Status status);
    public List<ShortPostResponse> filterPostForUser(Integer pageSize,Integer page,Customer customer,String category,Long cost,String startDate,String endDate);
    public PostResponse createPost(CreatePostDto createPostDTO, Long userId) throws Exception;
    public PostResponse updatePost(Long postId,CreatePostDto updatePostDTO, Long userId) throws Exception;
    public PostResponse deletePost(Long id, Long userId);
    public FeedbackResponse createNewFeedback(CreateFeedback createFeedback, Long postId, Long userId);
    public List<ShortPostResponse> filterPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir,FilterPostDto filter);
    public List<ShortPostResponse> searchPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir, String q);
    //admin
    public List<FeedbackResponse> getPostFeedback(Integer pageSize, Integer pageNo,Long postId);
    public boolean changeStatusPost(Long postId,Long userId, ChangeStatusDto changeStatus, boolean isAdmin) throws Exception;

}

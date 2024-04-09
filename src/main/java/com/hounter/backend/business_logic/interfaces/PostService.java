package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.CustomerDTO.PostOfUserRes;
import com.hounter.backend.application.DTO.FeedbackDto.CreateFeedback;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;
import com.hounter.backend.application.DTO.PostDto.*;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.shared.enums.Status;

import java.time.LocalDate;
import java.util.List;

public interface PostService {
    //Scheduler function
    void handlePostExpire(LocalDate date);
    //to all users
    Post findPostById(Long postId);
    PostResponse getPostById(Long postId, boolean isUser);
    List<ShortPostResponse> getAllPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir);
    List<ShortPostResponse> getAllPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir, Status status);
    //user
    List<ShortPostResponse> getAllPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir, Customer customer);
    List<ShortPostResponse> getAllPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir, Customer customer, Status status);
    List<ShortPostResponse> filterPostForUser(Integer pageSize,Integer page,Customer customer,String category,String cost,String startDate,String endDate, Status status);
    List<ShortPostResponse> searchOnMap(float latitude, float longitude, Integer pageSize, Integer pageNo);
    PostResponse createPost(CreatePostDto createPostDTO, Long userId) throws Exception;
    PostResponse updatePost(Long postId,UpdatePostDTO updatePostDTO, Long userId) throws Exception;
    PostResponse deletePost(Long id, Long userId);
    PostOfUserRes getPostDetailOfUser(Long postId);
    FeedbackResponse createNewFeedback(CreateFeedback createFeedback, Long postId, Long userId);
    List<ShortPostResponse> filterPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir,FilterPostDto filter);
    List<ShortPostResponse> searchPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir, String q);
    //admin
    List<FeedbackResponse> getPostFeedback(Integer pageSize, Integer pageNo,Long postId);
    boolean changeStatusPost(Long postId,Long userId, ChangeStatusDto changeStatus, boolean isAdmin) throws Exception;
    boolean changeStatus_Staff(Long postId, String status, Long staffId);
    List <SummaryPostDTO> findAvancedPost(FindPostDTO findPostDTO);
}

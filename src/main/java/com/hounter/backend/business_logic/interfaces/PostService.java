package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.PostDto.ChangeStatusDto;
import com.hounter.backend.application.DTO.PostDto.CreatePostDto;
import com.hounter.backend.application.DTO.PostDto.FilterPostDto;
import com.hounter.backend.application.DTO.PostDto.PostResponse;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
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

    public PostResponse createPost(CreatePostDto createPostDTO, Long userId) throws Exception;
    public PostResponse updatePost(Long postId,CreatePostDto updatePostDTO, Long userId) throws Exception;
    public PostResponse deletePost(Long id, Long userId);

    public List<ShortPostResponse> filterPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir,FilterPostDto filter);
    public List<ShortPostResponse> searchPost(Integer pageSize,Integer pageNo,String sortBy,String sortDir, String q);
    //admin
    public boolean changeStatusPost(Long postId,Long userId, ChangeStatusDto changeStatus, boolean isAdmin) throws Exception;

}

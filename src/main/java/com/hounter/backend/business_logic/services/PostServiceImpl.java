package com.hounter.backend.business_logic.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hounter.backend.application.DTO.PostDto.CreatePostDto;
import com.hounter.backend.application.DTO.PostDto.FilterPostDto;
import com.hounter.backend.application.DTO.PostDto.PostResponse;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.entities.Category;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.PostCost;
import com.hounter.backend.business_logic.interfaces.PostCostService;
import com.hounter.backend.business_logic.interfaces.PostImageService;
import com.hounter.backend.business_logic.interfaces.PostService;
import com.hounter.backend.business_logic.mapper.PostMapping;
import com.hounter.backend.data_access.repositories.CategoryRepository;
import com.hounter.backend.data_access.repositories.CustomerRepository;
import com.hounter.backend.data_access.repositories.PostRepository;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.CategoryNotFoundException;
import com.hounter.backend.shared.exceptions.ForbiddenException;
import com.hounter.backend.shared.exceptions.PostNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostImageService postImageService;

    @Autowired
    private PostCostService postCostService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentServiceImpl paymentService;

    @Override
    public PostResponse getPostById(Long postId) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if(optionalPost.isPresent()){
            return PostMapping.PostResponseMapping(optionalPost.get());
        }
        throw new PostNotFoundException("Cannot find post with id = " + postId);
    }

    private List<ShortPostResponse> mappListOfPost(List<Post> posts){
        List<ShortPostResponse> responses = new ArrayList<ShortPostResponse>();
        if (!posts.isEmpty()) {
            for (Post post : posts) {
                responses.add(PostMapping.getShortPostResponse(post));
            }
            return responses;
        } else {
            return null;
        }
    }
    @Override
    public List<ShortPostResponse> getAllPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,
            Status status) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        List<Post> posts = this.postRepository.findByStatus(status, pageable);
        return mappListOfPost(posts);
    }

    @Override
    public List<ShortPostResponse> getAllPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Post> posts = this.postRepository.findAll(pageable);

        List<ShortPostResponse> responses = new ArrayList<ShortPostResponse>();
        if (!posts.isEmpty()) {
            List<Post> postList = posts.getContent();
            for (Post post : postList) {
                responses.add(PostMapping.getShortPostResponse(post));
            }
            return responses;
        } else {
            return null;
        }
    }

    @Override
    public List<ShortPostResponse> getAllPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,
        Customer customer) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        List<Post> posts = this.postRepository.findByCustomer(customer, pageable);
        return mappListOfPost(posts);
    }

    @Override
    public List<ShortPostResponse> getAllPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,
        Customer customer, Status status) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        List<Post> posts = this.postRepository.findByCustomerAndStatus(customer,status, pageable);
        return mappListOfPost(posts);
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public PostResponse createPost(CreatePostDto createPostDTO, Long userId) throws Exception {
        Post post = PostMapping.createPostMapping(createPostDTO);
        Optional<Customer> op_cutomer = this.customerRepository.findById(userId);
        if (op_cutomer.isPresent()) {
            post.setCustomer(op_cutomer.get());
        } else {
            throw new Exception("No customer found!");
        }
        Optional<Category> op_category = this.categoryRepository.findById(createPostDTO.getCategory());
        if (op_category.isPresent()) {
            Category category = op_category.get();
            post.setCategory(category);
        } else {
            throw new CategoryNotFoundException("Cannot find category with id " + createPostDTO.getCategory());
        }

        Post saved_post = this.postRepository.save(post);
        PostCost postCost = this.postCostService.enrollPostToCost(saved_post, createPostDTO.getCost(),
                createPostDTO.getDays());
        this.paymentService.createPayment(postCost, userId);
        this.postImageService.storeImageOfPost(saved_post, createPostDTO.getImageUrls());
        return PostMapping.PostResponseMapping(saved_post);
    }

    @Override
    public PostResponse updatePost(Long postId, CreatePostDto updatePostDTO, Long userId) throws Exception {
        Optional<Post> op_post = this.postRepository.findById(postId);
        if (op_post.isPresent()) {
            Post post = op_post.get();
            if (!Objects.equals(post.getCustomer().getId(), userId)) {
                throw new ForbiddenException("Forbidden", HttpStatus.FORBIDDEN);
            } else {

            }
        } else {
            throw new PostNotFoundException("Cannot find post with id " + postId);
        }
        return null;
    }

    @Override
    public boolean deletePost(Long id, Long userId) {
        return false;
    }

    @Override
    public List<ShortPostResponse> filterPost(FilterPostDto filterPostDto) {
        List<Post> postList = this.postRepository.findAll();
        return null;
    }

    @Override
    public boolean changeStatusPost(Long postId) {
        return false;
    }

}

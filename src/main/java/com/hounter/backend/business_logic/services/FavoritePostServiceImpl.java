package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.FavoriteDto.FavoriteResponse;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.FavoritePost;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.interfaces.FavoritePostService;
import com.hounter.backend.business_logic.mapper.FavoriteMapping;
import com.hounter.backend.data_access.repositories.CustomerRepository;
import com.hounter.backend.data_access.repositories.FavoritePostRepository;
import com.hounter.backend.data_access.repositories.PostRepository;
import com.hounter.backend.shared.exceptions.PostNotFoundException;

import jakarta.el.ELException;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDate;
@Service
public class FavoritePostServiceImpl implements FavoritePostService {
    @Autowired
    private FavoritePostRepository favoritePostRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<FavoriteResponse> getAllFavoritePost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,Long userId) {
        Pageable pageable =  PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<FavoritePost> postLst = this.favoritePostRepository.findAll(pageable);
        List<FavoriteResponse> responseList = new ArrayList<>();
        if(!postLst.isEmpty()){
            for(FavoritePost post: postLst.getContent()){
                responseList.add(FavoriteMapping.responseMapping(post));
            }
            return responseList;
        }
        return null;
    }

    @Override
    public FavoriteResponse addPostToFavorite(Long userId, Long postId) throws Exception {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Optional<Customer> optionalCustomer = this.customerRepository.findById(userId);
            Customer customer = optionalCustomer.get();
            FavoritePost favoritePost = new FavoritePost();
            favoritePost.setPost(post);
            favoritePost.setCustomer(customer);
            favoritePost.setCreateAt(LocalDate.now());
            this.favoritePostRepository.save(favoritePost);
            return FavoriteMapping.responseMapping(favoritePost);
        }
        throw new PostNotFoundException("Can't find post with id " + postId);
        
    }

    @Override
    public FavoriteResponse deletePostFromFavorite(Long userId, Long postId) throws Exception{
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        Optional<Customer> optionalCustomer = this.customerRepository.findById(userId);
        if(optionalPost.isPresent() && optionalCustomer.isPresent()) {
            Post post = optionalPost.get();
            Customer customer = optionalCustomer.get();
            FavoritePost favoritePost = this.favoritePostRepository.findByCustomerAndPost(customer, post);
            this.favoritePostRepository.delete(favoritePost);
        }
        else if(optionalCustomer.isPresent()) {
            throw new PostNotFoundException("Can't find post with id " + postId);
        }
        throw new IllegalIdentifierException("User not found");
    }

}

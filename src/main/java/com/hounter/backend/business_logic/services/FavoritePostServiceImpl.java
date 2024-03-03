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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Optional<Customer> optionalCustomer = this.customerRepository.findById(userId);
        if(optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            List<FavoritePost> favoritePosts = this.favoritePostRepository.findByCustomer(customer,pageable);
            List<FavoriteResponse> responseList = new ArrayList<>();
            if(favoritePosts.isEmpty()){
                return null;
            }
            for(FavoritePost post: favoritePosts){
                responseList.add(FavoriteMapping.responseMapping(post));
            }
            return responseList;
        }
        throw new PostNotFoundException("Can't find post with user!" );
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public boolean addPostToFavorite(Long userId, Long postId) throws Exception {
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
            return true;
        }
        throw new PostNotFoundException("Can't find post with id " + postId);
        
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public boolean deletePostFromFavorite(Long userId, Long postId) throws Exception{
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        Optional<Customer> optionalCustomer = this.customerRepository.findById(userId);
        if(optionalPost.isPresent() && optionalCustomer.isPresent()) {
            Post post = optionalPost.get();
            Customer customer = optionalCustomer.get();
            FavoritePost favoritePost = this.favoritePostRepository.findByCustomerAndPost(customer, post);
            if(favoritePost != null) {
                this.favoritePostRepository.delete(favoritePost);
                return true;
            }
        }
        else if(optionalCustomer.isPresent()) {
            throw new PostNotFoundException("Can't find post with id " + postId);
        }
        return false;
    }

}

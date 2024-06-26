package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.FeedbackDto.CreateFeedback;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackDetailResponse;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Feedback;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.interfaces.FeedbackService;
import com.hounter.backend.business_logic.mapper.FeedbackMapping;
import com.hounter.backend.data_access.repositories.CustomerRepository;
import com.hounter.backend.data_access.repositories.FeedbackRepository;
import com.hounter.backend.data_access.repositories.PostRepository;
import com.hounter.backend.shared.exceptions.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<FeedbackResponse> getAllFeedback(Integer pageSize, Integer pageNo, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Feedback> feedback = this.feedbackRepository.findAll(pageable);
        if(!feedback.isEmpty()) {
            List<Feedback> feedbackList = feedback.getContent();
            List<FeedbackResponse> feedbackResponse = new ArrayList<FeedbackResponse>();
            for(Feedback item : feedbackList){
                feedbackResponse.add(FeedbackMapping.getResponse(item));
            }
            return feedbackResponse;
        }
        return null;
    }

    @Override
    public FeedbackDetailResponse getFeedbackById(Long id){
        Optional<Feedback> optionFeedback = this.feedbackRepository.findById(id);
        if(optionFeedback.isPresent()){
            Feedback feedback = optionFeedback.get();
            return FeedbackMapping.getDetailResponse(feedback);
        }
        return null;
    }

    @Override
    public List<FeedbackResponse> getFeedbackByPost(Long postId, Integer pageSize, Integer pageNo, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        List<Feedback> feedbacks = this.feedbackRepository.findByPost_id(postId, pageable);
        List<FeedbackResponse> responses = new ArrayList<>();
        for(Feedback feedback : feedbacks){
            responses.add(FeedbackMapping.getResponse(feedback));
        }
        return responses;
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public FeedbackResponse createFeedback(CreateFeedback createFeedback, Long postId, Long userId) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        Optional<Customer> optionalCustomer = this.customerRepository.findById(userId);
        if(optionalCustomer.isPresent() && optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Customer customer = optionalCustomer.get();
            Feedback feedback = new Feedback();
            feedback.setCustomer(customer);
            feedback.setPost(post);
            feedback.setCreateAt(LocalDate.now());
            feedback.setContent(createFeedback.getContent());
            Feedback saved = this.feedbackRepository.save(feedback);
            return FeedbackMapping.getResponse(saved);
        }
        else if(optionalCustomer.isPresent()){
            throw new PostNotFoundException("Can't find post with id " + postId);
        }
        return null;
    }

    @Override
    public Boolean deleteFeedbackById(Long id) {
        Optional<Feedback> optionalFeedback = this.feedbackRepository.findById(id);
        if(optionalFeedback.isPresent()){
            this.feedbackRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

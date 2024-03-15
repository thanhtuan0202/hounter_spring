package com.hounter.backend.business_logic.services;

import com.hounter.backend.business_logic.entities.Cost;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.PostCost;
import com.hounter.backend.business_logic.interfaces.PostCostService;
import com.hounter.backend.data_access.repositories.CostRepository;
import com.hounter.backend.data_access.repositories.PostCostRepository;
import com.hounter.backend.data_access.repositories.PostRepository;
import com.hounter.backend.shared.exceptions.CostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PostCostServiceImpl implements PostCostService {
    @Autowired
    private PostCostRepository postCostRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CostRepository costRepository;
    
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void enrollPostToCost(Long post_id, Long cost_id, Integer days) {
        Optional<Cost> op_cost = this.costRepository.findById(cost_id);
        Optional<Post> op_post = this.postRepository.findById(post_id);
        if(op_cost.isPresent() && op_post.isPresent()) {
            Post post = op_post.get();
            Cost cost = op_cost.get();
            PostCost cost_post = new PostCost();
            cost_post.setCost(cost);
            cost_post.setActiveDays(days);
            cost_post.setDate(LocalDate.now());
            this.postCostRepository.save(cost_post);
        }
        else{
            throw new CostNotFoundException("Fail to select cost or not found post!");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updatePostCost(PostCost postCost, Long costId, Integer days){
        Optional<Cost> op_cost = this.costRepository.findById(costId);
        if(op_cost.isPresent()) {
            Cost cost = op_cost.get();
            postCost.setCost(cost);
            postCost.setActiveDays(days);
            postCost.setDate(LocalDate.now());
            this.postCostRepository.save(postCost);
        }
        else{
            throw new CostNotFoundException("Fail to select cost or not found post!");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public PostCost enrollPostToCost(Post post, Long cost_id, Integer days) {
        Optional<Cost> op_cost = this.costRepository.findById(cost_id);
        if(op_cost.isPresent()) {
            Cost cost = op_cost.get();
            PostCost cost_post = new PostCost();
            cost_post.setCost(cost);
            cost_post.setActiveDays(days);
            cost_post.setDate(LocalDate.now());
            return this.postCostRepository.save(cost_post);
        }
        else{
            throw new CostNotFoundException("Fail to select cost or not found post!");
        }
    }

    @Override
    public PostCost findByPost(Post post) {
        return this.postCostRepository.findByPost(post);
    }

}

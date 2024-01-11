package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.PostCost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCostRepository extends JpaRepository<PostCost, Long> {
    public List<PostCost> findByPost(Post post, Pageable pageable);
}

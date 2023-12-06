package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Feedback;
import com.hounter.backend.business_logic.entities.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    public List<Feedback> findByPost_id(Long post_id, Pageable pageable);
}

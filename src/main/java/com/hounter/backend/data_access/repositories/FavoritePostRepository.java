package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.FavoritePost;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.shared.part_entity.FavoritePostId;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritePostRepository extends JpaRepository<FavoritePost, FavoritePostId> {
    public FavoritePost findByCustomerAndPost(Customer customer, Post post);
    public List<FavoritePost> findByCustomer(Customer customer, Pageable pageable);
}

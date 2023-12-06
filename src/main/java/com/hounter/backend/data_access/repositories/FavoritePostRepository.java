package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.FavoritePost;
import com.hounter.backend.business_logic.entities.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritePostRepository extends JpaRepository<FavoritePost, Long> {
    public FavoritePost findByCustomerAndPost(Customer customer, Post post);
}

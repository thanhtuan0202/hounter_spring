package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.FavoritePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritePostRepository extends JpaRepository<FavoritePost, Long> {
}

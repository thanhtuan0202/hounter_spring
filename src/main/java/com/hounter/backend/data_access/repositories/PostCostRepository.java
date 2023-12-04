package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.PostCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCostRepository extends JpaRepository<PostCost, Long> {
}

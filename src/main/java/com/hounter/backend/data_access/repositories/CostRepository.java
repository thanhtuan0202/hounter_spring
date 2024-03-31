package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<Cost, Long> {
    Cost findByName(String cost);
}

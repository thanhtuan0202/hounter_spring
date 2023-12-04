package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

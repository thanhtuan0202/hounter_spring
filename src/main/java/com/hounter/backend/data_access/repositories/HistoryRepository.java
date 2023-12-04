package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}

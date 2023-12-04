package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<BalanceHistory, Long> {
}

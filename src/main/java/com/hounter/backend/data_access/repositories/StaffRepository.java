package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUsername(String username);
}

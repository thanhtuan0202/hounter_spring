package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}

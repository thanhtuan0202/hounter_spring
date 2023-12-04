package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}

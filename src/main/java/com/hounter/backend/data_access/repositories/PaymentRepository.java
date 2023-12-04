package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

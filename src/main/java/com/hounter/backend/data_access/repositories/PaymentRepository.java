package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.entities.PostCost;
import com.hounter.backend.shared.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPostCost(PostCost post);
    List<Payment> findByCustomer(Customer customer, Pageable pageable);
    List<Payment> findByExpireAtAndStatus(LocalDate expireAt, PaymentStatus status);
    Optional<Payment> findByVnPayTxnRef(String vnPayTxnRef);
}

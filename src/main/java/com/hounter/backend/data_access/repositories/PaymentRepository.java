package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.entities.PostCost;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPostCost(PostCost post);
    List<Payment> findByCustomer(Customer customer, Pageable pageable);
    Payment findByPostNum(Long postNum);
}

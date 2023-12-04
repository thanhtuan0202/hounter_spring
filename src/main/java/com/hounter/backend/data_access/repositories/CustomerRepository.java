package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

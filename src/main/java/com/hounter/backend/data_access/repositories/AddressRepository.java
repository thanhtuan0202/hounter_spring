package com.hounter.backend.data_access.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hounter.backend.business_logic.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
    
}

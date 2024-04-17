package com.hounter.backend.data_access.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hounter.backend.business_logic.entities.Province;

public interface ProvinceRepository extends JpaRepository<Province, Integer>{
    public Optional<Province> findByCode(Integer code);
}

package com.hounter.backend.data_access.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hounter.backend.business_logic.entities.District;
import com.hounter.backend.business_logic.entities.Province;

public interface DistrictRepository extends JpaRepository<District, Integer>{
    public List<District> findByProvince(Province province);
}

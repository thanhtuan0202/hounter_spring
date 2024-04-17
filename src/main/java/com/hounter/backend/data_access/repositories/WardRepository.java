package com.hounter.backend.data_access.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hounter.backend.business_logic.entities.Ward;

public interface WardRepository extends JpaRepository<Ward, Integer>{
    public Optional<Ward> findByCode(Integer code);

    @Query(value = "select w.* from ward w, district d where w.district_id = d.code and w.name_with_type like %:ward and d.name_with_type like %:district ", nativeQuery = true)
    public Ward searchByDistrictAndWard(String district, String ward);
}

package com.hounter.backend.data_access.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hounter.backend.business_logic.entities.Place;
import com.hounter.backend.shared.enums.PlaceType;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long>{
    @Query("SELECT p FROM Place p WHERE p.district LIKE %:district%")
    List<Place> findByDistrict(String district, Pageable pageable);

    List<Place> findByType(PlaceType type);
    @Query("SELECT p from Place p where p.district LIKE %:district% and p.type = :type")
    List <Place> findByTypeAndDistrict(String district, PlaceType type, Pageable pageable);
}

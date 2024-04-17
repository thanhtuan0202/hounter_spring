package com.hounter.backend.data_access.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hounter.backend.business_logic.entities.Place;
import com.hounter.backend.business_logic.entities.Ward;
import com.hounter.backend.shared.enums.PlaceType;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long>{
    List <Place> findByWard(Ward ward, Pageable pageable);

    List<Place> findByType(PlaceType type);
    @Query("SELECT p from Place p where p.ward = :ward and p.type = :type")
    List <Place> findByTypeAndWard(Ward ward, PlaceType type, Pageable pageable);
}

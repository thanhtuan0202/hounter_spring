package com.hounter.backend.data_access.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hounter.backend.business_logic.entities.Notify;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long>{
    List <Notify> findByAccount_Id(Long accountId);
}

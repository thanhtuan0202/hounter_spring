package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Notify;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long>{
    List <Notify> findByAccount(Account account, Pageable pageable);
}

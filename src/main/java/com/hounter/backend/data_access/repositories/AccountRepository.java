package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Post;

import jakarta.persistence.EntityManager;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    default List<Account> search(String searchTerm, EntityManager entityManager) {
        SearchSession searchSession = Search.session(entityManager);

        return searchSession.search(Account.class)
                .where(f -> f.match()
                        .fields("username")
                        .matching(searchTerm))
                .fetchHits(5);
    }
}
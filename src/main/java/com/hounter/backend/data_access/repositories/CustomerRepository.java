package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Customer;

import jakarta.persistence.EntityManager;

import java.util.List;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    default List<Customer> search(String searchTerm, EntityManager entityManager) {
        SearchSession searchSession = Search.session(entityManager);

        return searchSession.search(Customer.class)
                .where(f -> f.match()
                        .fields("username", "full_name")
                        .matching(searchTerm))
                .fetchHits(5);
    }
}

package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Category;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.shared.enums.Status;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByStatus(Status status, Pageable page);
    List<Post> findByStatus(Status status);
    List<Post> findByStatusAndCategory(Status status, Category category, Pageable page);
    List<Post> findByCustomer(Customer customer, Pageable page);
    List<Post> findByCustomerAndStatus(Customer customer, Status status, Pageable page);
    @Query(value = "SELECT * FROM posts p WHERE distance_haversine(p.latitude, p.longitude, :latitude, :longitude) < 3", nativeQuery = true)
    List<Post> findPostNearYou(@Param("latitude") float latitude, @Param("longitude") float longitude, Pageable pageable);


    default List<Post> search(String searchTerm, EntityManager entityManager) {
        SearchSession searchSession = Search.session(entityManager);

        return searchSession.search(Post.class)
                .where(f -> f.match()
                        .fields("title", "description")
                        .matching(searchTerm))
                .fetchHits(5);
    }
}

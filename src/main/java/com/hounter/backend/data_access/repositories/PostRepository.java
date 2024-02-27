package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.Category;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.shared.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByStatus(Status status,Pageable page);
    public List<Post> findByStatusAndCategory(Status status, Category category,Pageable page);
    public List<Post> findByCustomer(Customer customer,Pageable page);    
    public List<Post> findByCustomerAndStatus(Customer customer,Status status,Pageable page);
    @Query(value = "SELECT * FROM posts p WHERE distance_haversine(p.latitude, p.longitude, :latitude, :longitude) < 3", nativeQuery = true)
    public List<Post> findPostNearYou(@Param("latitude") float latitude,@Param("longitude") float longitude, Pageable pageable);
}

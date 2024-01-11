package com.hounter.backend.data_access.repositories;

import com.hounter.backend.business_logic.entities.PostImage;
import com.hounter.backend.shared.part_entity.PostImageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, PostImageId> {
}

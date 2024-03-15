package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.business_logic.entities.Post;

import java.util.List;

public interface PostImageService {
    void storeImageOfPost(Long postId, List<String> imageUrls);
    void storeImageOfPost(Post post, List<String> imageUrls);
    void deleteImageOfPost(Post post, List<String> deleteImages);
}

package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.business_logic.entities.Post;

import java.util.List;

public interface PostImageService {
    public void storeImageOfPost(Long postId, List<String> imageUrls);
    public void storeImageOfPost(Post post, List<String> imageUrls);
}

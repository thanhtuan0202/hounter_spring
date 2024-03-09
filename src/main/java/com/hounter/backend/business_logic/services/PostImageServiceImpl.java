package com.hounter.backend.business_logic.services;

import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.PostImage;
import com.hounter.backend.business_logic.interfaces.PostImageService;
import com.hounter.backend.data_access.repositories.PostImageRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostImageServiceImpl implements PostImageService {
    @Autowired
    private PostImageRepository postImageRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void storeImageOfPost(Long postId, List<String> imageUrls) {

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void storeImageOfPost(Post post, List<String> imageUrls) {
        for(String item: imageUrls) {
            PostImage post_img = new PostImage();
            post_img.setPost(post);
            post_img.setImageUrl(item);
            this.postImageRepository.save(post_img);
        }
    }

    @Override
    public void deleteImageOfPost(Post post, List<String> deleteImages) {
        for(String item: deleteImages) {
            this.postImageRepository.deleteByPostAndImageUrl(post, item);
        }
    }
}

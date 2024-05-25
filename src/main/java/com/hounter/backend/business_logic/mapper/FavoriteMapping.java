package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.FavoriteDto.FavoriteResponse;
import com.hounter.backend.application.DTO.PostDto.ShortCustomer;
import com.hounter.backend.business_logic.entities.FavoritePost;
import com.hounter.backend.business_logic.entities.PostImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoriteMapping {
    public static FavoriteResponse responseMapping(FavoritePost post){
        FavoriteResponse response = new FavoriteResponse();
        Set<PostImage> images = post.getPost().getPostImages();
        List<PostImage> lst = new ArrayList<>(images);
        response.setId(post.getPost().getId());
        response.setTitle(post.getPost().getTitle());
        response.setPrice(post.getPost().getPrice());
        response.setAddress(post.getPost().getAddress().toString());
        if (images.size() > 0) {
            response.setImage(lst.get(0).getImageUrl());
        }
        response.setArea(post.getPost().getArea());
        response.setOwner(new ShortCustomer(post.getPost().getCustomer().getId(), post.getPost().getCustomer().getFull_name(), post.getPost().getCustomer().getAvatar()));
        response.setCategory(post.getPost().getCategory().getId());
        response.setCreateAt(post.getPost().getCreateAt());
        response.setExpireAt(post.getPost().getExpireAt());
        return response;
    }
}

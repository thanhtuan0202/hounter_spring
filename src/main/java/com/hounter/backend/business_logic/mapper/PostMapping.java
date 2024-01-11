package com.hounter.backend.business_logic.mapper;

import java.time.LocalDate;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


import com.hounter.backend.application.DTO.PostDto.CreatePostDto;
import com.hounter.backend.application.DTO.PostDto.PostResponse;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.PostCost;
import com.hounter.backend.business_logic.entities.PostImage;
import com.hounter.backend.shared.enums.Status;

public class PostMapping {
    public static ShortPostResponse getShortPostResponse(Post post) {
        Set<PostCost> costs = post.getPostCosts();
        Iterator<PostCost> it = costs.iterator();
        PostCost last = new PostCost();
        while (it.hasNext()) {
            last = it.next();
        }

        Set<PostImage> images = post.getPostImages();
        List<PostImage> lst = new ArrayList<>(images);
        
        return new ShortPostResponse(
            post.getId(),
            post.getTitle(),
            post.getPrice(),
            post.getArea(),
            post.getFullAdress(),
            post.getCustomerName(),
            post.getCustomerPhone(),
            post.getCreateAt(),
            post.getExpireAt(),
            post.getCategory().getId(),
            last.getId(),
            post.getStatus(),
            lst.get(0).getImageUrl()
        );
    }

    public static Post createPostMapping(CreatePostDto create){
        Post new_post = new Post();
        new_post.setTitle(create.getTitle());
        new_post.setPrice(create.getPrice());
        new_post.setDescription(create.getDescription());
        new_post.setArea(create.getArea());
        new_post.setFullAdress(create.getFullAddress());
        new_post.setCity(create.getCity());
        new_post.setCounty(create.getCounty());
        new_post.setDistrict(create.getDistrict());
        new_post.setCustomerName(create.getCustomerName());
        new_post.setCustomerPhone(create.getCustomerPhone());
        new_post.setCreateAt(LocalDate.now());
        new_post.setUpdateAt(LocalDate.now());
        new_post.setStatus(Status.active);
        new_post.setNotes(create.getNote());
        return new_post;
    }

    public static PostResponse PostResponseMapping(Post post){
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setDescription(post.getDescription());
        response.setPrice(post.getPrice());
        response.setArea(post.getArea());
        response.setCategory(post.getCategory().getId());
        response.setCustomer(post.getCustomer().getId());
        response.setFullAddress(post.getFullAdress());
        response.setCity(post.getCity());
        response.setCounty(post.getCounty());
        response.setDistrict(post.getDistrict());
        response.setOwnerName(post.getCustomerName());
        response.setOwnerPhone(post.getCustomerPhone());
        response.setCreateAt(post.getCreateAt());
        response.setExpireAt(post.getExpireAt());
        response.setStatus(post.getStatus());
        response.setLatitude(post.getLatitude());
        response.setLongitude(post.getLongitude());
        Set<PostImage> images = post.getPostImages();
        List<PostImage> lst = new ArrayList<>(images);
        List<String> img = new ArrayList<String>();
        for (PostImage item : lst){
            img.add(item.getImageUrl());
        }
        response.setImageList(img);
        return response;
    }

    public static PostResponse PostResponseMapping1(Post post){
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setDescription(post.getDescription());
        response.setPrice(post.getPrice());
        response.setArea(post.getArea());
        response.setCategory(post.getCategory().getId());
        response.setCustomer(post.getCustomer().getId());
        response.setFullAddress(post.getFullAdress());
        response.setCity(post.getCity());
        response.setCounty(post.getCounty());
        response.setDistrict(post.getDistrict());
        response.setOwnerName(post.getCustomerName());
        response.setOwnerPhone(post.getCustomerPhone());
        response.setCreateAt(post.getCreateAt());
        response.setStatus(post.getStatus());
        response.setExpireAt(post.getExpireAt());
        return response;
    }
}

package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.AddressDTO;
import com.hounter.backend.application.DTO.CustomerDTO.PostOfUserRes;
import com.hounter.backend.application.DTO.PostDto.CreatePostDto;
import com.hounter.backend.application.DTO.PostDto.PostResponse;
import com.hounter.backend.application.DTO.PostDto.ShortCustomer;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.PostCost;
import com.hounter.backend.business_logic.entities.PostImage;
import com.hounter.backend.shared.enums.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PostMapping {
    public static ShortPostResponse getShortPostResponse(Post post, PostCost cost) {
        Set<PostImage> images = post.getPostImages();
        List<PostImage> lst = new ArrayList<>(images);
        String imageUrl = null;
        if (!lst.isEmpty()) {
            imageUrl = lst.get(0).getImageUrl();
        }
        return new ShortPostResponse(
            post.getId(),
            post.getTitle(),
            post.getPrice(),
            post.getArea(),
            post.getAddress().toString(),
            new ShortCustomer(post.getCustomer().getId(), post.getCustomer().getFull_name(), post.getCustomer().getAvatar()),
            post.getCustomerPhone(),
            post.getCreateAt(),
            post.getExpireAt(),
            post.getCategory().getId(),
            cost.getCost().getName(),
            post.getStatus(),
            imageUrl
        );
    }

    public static Post createPostMapping(CreatePostDto create){
        Post new_post = new Post();
        new_post.setTitle(create.getTitle());
        new_post.setPrice(create.getPrice());
        new_post.setDescription(create.getDescription());
        new_post.setArea(create.getArea());
        new_post.setCustomerName(create.getCustomerName());
        new_post.setCustomerPhone(create.getCustomerPhone());
        new_post.setCreateAt(LocalDate.now());
        new_post.setUpdateAt(LocalDate.now());
        new_post.setStatus(Status.waiting);
        if(create.getNote() != null && !create.getNote().isBlank()){
            new_post.setNotes(create.getNote());
        }
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
        response.setOwner(new ShortCustomer(post.getCustomer().getId(), post.getCustomer().getFull_name(), post.getCustomer().getAvatar()));
        response.setAddress(new AddressDTO.AddressResDTO(post.getAddress()));
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
        response.setOwner(new ShortCustomer(post.getCustomer().getId(), post.getCustomer().getFull_name(), post.getCustomer().getAvatar()));
        response.setAddress(new AddressDTO.AddressResDTO(post.getAddress()));
        response.setOwnerName(post.getCustomerName());
        response.setOwnerPhone(post.getCustomerPhone());
        response.setCreateAt(post.getCreateAt());
        response.setStatus(post.getStatus());
        response.setExpireAt(post.getExpireAt());
        return response;
    }

    public static PostOfUserRes PostOfUserMapping(Post post){
        PostOfUserRes postDetail = new PostOfUserRes();
        postDetail.setId(post.getId());
        postDetail.setTitle(post.getTitle());
        postDetail.setDescription(post.getDescription());
        postDetail.setPrice(post.getPrice());
        postDetail.setArea(post.getArea());
        postDetail.setCategory(post.getCategory().getName());
        postDetail.setAddress(new AddressDTO.AddressResIdDTO(post.getAddress()));
        postDetail.setOwnerName(post.getCustomerName());
        postDetail.setOwnerPhone(post.getCustomerPhone());
        postDetail.setNotes(post.getNotes());
        Set<PostImage> images = post.getPostImages();
        List<PostImage> lst = new ArrayList<>(images);
        List<String> img = new ArrayList<String>();
        for (PostImage item : lst){
            img.add(item.getImageUrl());
        }
        postDetail.setImageList(img);
        return postDetail;
    }
}

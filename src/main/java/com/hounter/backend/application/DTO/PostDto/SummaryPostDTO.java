package com.hounter.backend.application.DTO.PostDto;

import com.hounter.backend.business_logic.entities.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SummaryPostDTO {
    Long id;
    String title;
    Integer price;
    Integer area;
    Float latitude;
    Float longitude;

    public SummaryPostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.area = post.getArea();
        this.latitude = post.getLatitude().floatValue();
        this.longitude = post.getLongitude().floatValue();
    }
}
